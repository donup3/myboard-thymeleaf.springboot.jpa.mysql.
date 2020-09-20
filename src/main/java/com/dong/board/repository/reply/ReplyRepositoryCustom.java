package com.dong.board.repository.reply;

import com.dong.board.domain.Board;
import com.dong.board.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepositoryCustom {
    Page<Reply> getRepliesOfBoard(Board board, Pageable pageable);
}
