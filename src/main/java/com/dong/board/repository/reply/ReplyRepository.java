package com.dong.board.repository.reply;

import com.dong.board.domain.Reply;
import org.springframework.data.repository.CrudRepository;

public interface ReplyRepository extends CrudRepository<Reply,Long> ,ReplyRepositoryCustom{

}
