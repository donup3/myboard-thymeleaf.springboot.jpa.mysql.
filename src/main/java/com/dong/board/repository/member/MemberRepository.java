package com.dong.board.repository.member;


import com.dong.board.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member,String> {

}
