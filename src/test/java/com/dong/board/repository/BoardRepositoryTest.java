package com.dong.board.repository;

import com.dong.board.domain.Board;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Rollback(value = false)
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 더미데이터생성(){
        for(int i=0;i<=300;i++){
            Board board=new Board();
            board.setTitle("테스트제목"+i);
            board.setContent("테스트내용"+i);
            board.setWriter("user0"+(i%10));

            boardRepository.save(board);
        }
    }
}