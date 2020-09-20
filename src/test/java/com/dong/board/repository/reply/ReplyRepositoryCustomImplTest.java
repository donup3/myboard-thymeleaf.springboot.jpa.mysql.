package com.dong.board.repository.reply;

import com.dong.board.domain.Board;
import com.dong.board.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryCustomImplTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void 답글페이징테스트(){
        Board board=new Board();
        board.setBno(303L);

        PageRequest page = PageRequest.of(0, 10, Sort.Direction.ASC,"rno");

        Page<Reply> result = replyRepository.getRepliesOfBoard(board, page);
        assertEquals(result.getTotalElements(),10);
        result.getContent().forEach(reply -> {
            System.out.println("reply = " + reply);
        });
    }
}