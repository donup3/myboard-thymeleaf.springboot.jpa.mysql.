package com.dong.board.controller;

import com.dong.board.domain.Board;
import com.dong.board.dto.PageDto;
import com.dong.board.dto.PageMaker;
import com.dong.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageDto")PageDto pageDto, Model model){
        log.info("get list.......");
        Pageable pageable = pageDto.makePageable(0, "bno");

        Page<Board> result = boardRepository.findAll(pageDto.getType(), pageDto.getKeyword(), pageable);

        model.addAttribute("result",new PageMaker(result));
    }

    @GetMapping("/register")
    public void registerGET(@ModelAttribute("board")Board board){
        log.info("register board...");
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("board")Board board, RedirectAttributes rttr){
        log.info("register board post...");

        boardRepository.save(board);
        rttr.addFlashAttribute("msg","success");

        return "redirect:/board/list";
    }
}
