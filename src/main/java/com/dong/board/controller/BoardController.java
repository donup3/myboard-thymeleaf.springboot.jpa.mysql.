package com.dong.board.controller;

import com.dong.board.domain.Board;
import com.dong.board.domain.BoardAttach;
import com.dong.board.dto.PageDto;
import com.dong.board.dto.PageMaker;
import com.dong.board.repository.attach.BoardAttachRepository;
import com.dong.board.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardAttachRepository boardAttachRepository;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageDto") PageDto pageDto, Model model) {
        log.info("get list.......");
        Pageable pageable = pageDto.makePageable(0, "bno");

        Page<Board> result = boardRepository.findAll(pageDto.getType(), pageDto.getKeyword(), pageable);
        model.addAttribute("result", new PageMaker(result));
    }

    @GetMapping("/register")
    public void registerGET(@ModelAttribute("board") Board board) {
        log.info("register board...");
    }

    @PostMapping("/register")
    @Transactional
    public String registerPost(@ModelAttribute("board") Board board, RedirectAttributes rttr) {
        Board saveBoard = boardRepository.save(board);

        rttr.addFlashAttribute("msg", "success");

        if (board.getAttachList() != null) {
            board.getAttachList().forEach(boardAttach -> {
                boardAttach.setBoard(saveBoard);
                boardAttachRepository.save(boardAttach);
            });
        }

        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageDto") PageDto pageDto, Model model) {
        log.info("BNO: " + bno);
        boardRepository.findById(bno).ifPresent(board -> model.addAttribute("board", board));
    }

    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageDto") PageDto pageDto, Model model) {
        log.info("Modify bno: " + bno);

        boardRepository.findById(bno).ifPresent(board -> model.addAttribute("board", board));
    }

    @PostMapping("/modify")
    public String modify(Board board, PageDto pageDto, RedirectAttributes rttr) {
        boardRepository.findById(board.getBno()).ifPresent(origin -> {
            origin.setTitle(board.getTitle());
            origin.setContent(board.getContent());

            boardRepository.save(origin);

            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("bno", origin.getBno());
        });
        rttr.addAttribute("page", pageDto.getPage());
        rttr.addAttribute("size", pageDto.getSize());
        rttr.addAttribute("type", pageDto.getType());
        rttr.addAttribute("keyword", pageDto.getKeyword());

        return "redirect:/board/view";
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageDto pageDto, RedirectAttributes rttr) {
        log.info("delete board bno: " + bno);
        boardRepository.deleteById(bno);

        rttr.addFlashAttribute("msg", "success");

        rttr.addAttribute("page", pageDto.getPage());
        rttr.addAttribute("size", pageDto.getSize());
        rttr.addAttribute("type", pageDto.getType());
        rttr.addAttribute("keyword", pageDto.getKeyword());

        return "redirect:/board/list";
    }

    @GetMapping("/getAttachList")
    @ResponseBody
    public ResponseEntity<List<BoardAttach>> getAttachList(Long bno){
        List<BoardAttach> boardAttachList = boardAttachRepository.findByBno(bno);

        return new ResponseEntity<>(boardAttachList, HttpStatus.OK);
    }

}
