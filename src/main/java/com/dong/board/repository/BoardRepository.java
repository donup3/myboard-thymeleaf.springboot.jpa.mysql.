package com.dong.board.repository;

import com.dong.board.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board,Long>,BoardCustomRepository {

}
