package com.dong.board.repository.reply;

import com.dong.board.domain.Board;
import com.dong.board.domain.Reply;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dong.board.domain.QReply.*;

public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ReplyRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Reply> getRepliesOfBoard(Board board, Pageable pageable) {
        QueryResults<Reply> result = queryFactory.selectFrom(reply)
                .where(reply.board.eq(board))
                .orderBy(reply.rno.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Reply> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
