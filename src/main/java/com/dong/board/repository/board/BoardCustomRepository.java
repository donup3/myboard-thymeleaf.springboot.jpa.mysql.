package com.dong.board.repository.board;

import com.dong.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

    Page<Board> findAll(String type,String keyword,Pageable pageable);
}
