package com.dong.board.repository;

import com.dong.board.domain.Board;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardCustomRepositoryImplTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 페이징테스트() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<Board> result = boardRepository.findAll(null, null, pageable);

        assertEquals(result.getSize(), 20);
        result.getContent().forEach(board -> System.out.println("board = " + board));

    }

    @Test
    public void 검색조건페이징테스트() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<Board> result = boardRepository.findAll("t", "10", pageable);

        result.getContent().forEach(board -> System.out.println("board = " + board));

    }
}