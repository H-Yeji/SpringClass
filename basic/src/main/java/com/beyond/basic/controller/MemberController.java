package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequiredArgsConstructor
public class MemberController {

    /**
     * 의존성 주입 (DI) 방법
     * (1) 생성자 주입 방식 -> 가장 많이 사용하는 방식
     * (1)-1 장점
     * : final을 통해 상수로 사용 가능 / 다형성 구현 가능 / 순환 참조 방지 ⭐
     */
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService; // 이름이 같아서 (다형성x) this 사용
    }

    /**
     * 의준성 주입 (DI) 방법
     * (2) 필드 주입 방식 (Autowired만 사용)
     *
     * (1)번 방법과 비교하기 ⭐
     * final 키워드 사용할 수 없음 -> 재할당이 가능해져서 안정성 떨어짐
     * 그리고 이렇게 쓰게되면 다형성 구현할 때 불편함 => 위 생성자 주입 방법에선 MemberService 부분을 jdbc로 바꾸면
     * 왼쪽은 인터페이스라 아래 코드에서 수정할 부분이 없었는데,
     * 아래 필드 주입 방법에선 MemberService부분을 바꾸면 아래 코드에서도 다 바꿔줘야 함
     */
//    @Autowired
//    private MemberService memberService;

    /**
     * 의존성 주입 (DI) 방법
     * (3) 어노테이션(RequiredArgs) 이용
     * @RequiredArgsConstructor
     * : @NonNull 어노테이션, final 키워드가 붙어있는 필드를 대상으로 생성자 생성
     */
    // final 쓰거나 NonNull 어노테이션 달고 클래스 상단에 @Required어쩌구
//    private final MemberService memberService;
//    @NonNull
//    private MemberService memberService;


    /**
     * 회원 목록 조회
     */
    @GetMapping("/member/list")
    public String findMemberList(Model model) {

        List<MemberResDto> memberResDtoList = memberService.memberList();
        model.addAttribute("memberResDtoList", memberResDtoList);

        return "member/memberList";
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/member/detail/{id}")
    // int 또는 long 받을 경우, 스프링에서 알아서 형변환 (String -> Long)
    public String memberDetail(@PathVariable Long id, Model model) {

        MemberDetailResDto memberDto = memberService.memberDetail(id);

        model.addAttribute("memberDto", memberDto);
        return "member/memberDetail";
    }

    /**
     * 회원 가입
     */
    @GetMapping("/member/create")
    public String createMember() {

        return "member/createMember";
    }

    // Member > MemberRequDto
    @PostMapping("/member/create")
    //@ResponseBody
    public String createMemberPost(@ModelAttribute MemberReqDto dto, Model model) {

        try {
            memberService.memberCreate(dto);
            return "redirect:/member/list";
        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "member/memberError";
        }
    }

    /**
     * 홈 가기
     */
    @GetMapping("/")
    public String home() {
        return "member/home";
    }
}
