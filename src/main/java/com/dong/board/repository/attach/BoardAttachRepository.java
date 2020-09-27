package com.dong.board.repository.attach;

import com.dong.board.domain.BoardAttach;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardAttachRepository extends CrudRepository<BoardAttach,String> {
    @Query("select a from BoardAttach a where a.board.bno=:bno")
    List<BoardAttach> findByBno(@Param(("bno")) Long bno);

    @Transactional
    @Modifying
    @Query(value = "delete from tbl_attach where bno=:bno",nativeQuery = true)
    void deleteByBno(@Param("bno") Long bno);
}
