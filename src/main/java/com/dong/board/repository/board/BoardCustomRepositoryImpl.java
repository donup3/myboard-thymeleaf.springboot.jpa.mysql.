package com.dong.board.repository.board;

import com.dong.board.domain.Board;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dong.board.domain.QBoard.*;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Board> findAll(String type, String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(board.bno.gt(0));
        if(type!=null){
            switch (type){
                case "t":
                    builder.and(board.title.like("%"+keyword+"%"));
                    break;
                case "w":
                    builder.and(board.writer.like("%"+keyword+"%"));
                    break;
                case "c":
                    builder.and(board.content.like("%"+keyword+"%"));
                    break;
                default:
                    break;
            }
        }

        QueryResults<Board> result1 = queryFactory.selectFrom(board)
                .orderBy(board.bno.desc())
                .where(builder)
                .leftJoin(board.replies).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        QueryResults<Board> result2 = queryFactory.selectFrom(board)
                .orderBy(board.bno.desc())
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = result1.getResults();
        long total = result2.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
