package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    /**
     * 회원 목록 조회
     */
    @GetMapping("/member/list")
    public String findMemberList() {

        return "member/memberList";
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/member/{id}")
    // int 또는 long 받을 경우, 스프링에서 알아서 형변환 (String -> Long)
    public String memberDetail(@PathVariable Long id, Model model) {

        model.addAttribute("id", id);
        return "member/memberDetail";
    }

    /**
     * 회원 가입
     */
    @GetMapping("/member/create")
    public String createMember() {

        return "member/createMember";
    }
    @PostMapping("/member/create")
    @ResponseBody
    public String createMemberPost(@ModelAttribute Member member) {

        System.out.println(member);
        return "ok";
    }
}
