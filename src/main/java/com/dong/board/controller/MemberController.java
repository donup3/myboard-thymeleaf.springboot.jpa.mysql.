package com.dong.board.controller;

import com.dong.board.domain.Member;
import com.dong.board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public void join() {

    }

    @Transactional
    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member") Member member) {
        String encryptPw = passwordEncoder.encode(member.getUpw());
        member.setUpw(encryptPw);

        memberRepository.save(member);

        return "/member/joinResult";
    }
}
