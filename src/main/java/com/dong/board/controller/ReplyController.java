package com.dong.board.controller;

import com.dong.board.domain.Board;
import com.dong.board.domain.Reply;
import com.dong.board.dto.PageDto;
import com.dong.board.dto.PageMaker;
import com.dong.board.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/replies/pages")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyRepository replyRepository;

    @PostMapping("/{bno}/{page}")
    @Transactional
    public ResponseEntity<PageMaker<Reply>> addReply(@PathVariable("bno")Long bno, @PathVariable("page")int page,@RequestBody Reply reply){
        log.info("Reply Controller addReply bno: "+bno);
        Board board=new Board();
        board.setBno(bno);
        reply.setBoard(board);

        replyRepository.save(reply);

        Pageable pageable = makePageDto(page);

        return new ResponseEntity<>(new PageMaker( getListByBoard(board,pageable)),HttpStatus.CREATED);
    }


    @GetMapping("/{bno}/{page}")
    public ResponseEntity<PageMaker<Reply>> getReplies(@PathVariable("bno")Long bno,@PathVariable("page")int page){
        Board board=new Board();
        board.setBno(bno);

        Pageable pageable = makePageDto(page);

        return new ResponseEntity<>(new PageMaker(getListByBoard(board,pageable)),HttpStatus.OK);
    }

    private Page getListByBoard(Board board, Pageable pageable) throws RuntimeException{
//        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.ASC, "rno");

        return replyRepository.getRepliesOfBoard(board,pageable);
    }

    private Pageable makePageDto(@PathVariable("page") int page) {
        PageDto pageDto = new PageDto();
        pageDto.setPage(page);
        return pageDto.makePageable(1, "rno");
    }
}
