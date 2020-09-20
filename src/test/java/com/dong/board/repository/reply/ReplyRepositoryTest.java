package com.dong.board.repository.reply;

import com.dong.board.domain.Board;
import com.dong.board.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void 더미데이터생성(){

        Long[] arr={301L,302L,303L};

        Arrays.stream(arr).forEach(num->{
            Board board=new Board();
            board.setBno(num);

            IntStream.range(0,10).forEach(i->{
                Reply reply=new Reply();
                reply.setReplyText("샘플 댓글"+i);
                reply.setReplyer("샘플replyer"+i);
                reply.setBoard(board);

                replyRepository.save(reply);
            });
        });
    }
}