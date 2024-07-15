package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.domain.MemberUpdateDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 화면을 출력하는 것이 아니라 json 데이터를 전송하기
 */
@RestController //@ResponseBody + @Controller
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService; // 이름이 같아서 (다형성x) this 사용
    }


    /**
     * 회원 목록 조회
     */
    @GetMapping("/member/list")
    public List<MemberResDto> findMemberList() {

        List<MemberResDto> memberResDtoList = memberService.memberList();

        return memberResDtoList;
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/member/detail/{id}")
    public MemberDetailResDto memberDetail(@PathVariable Long id) {

        MemberDetailResDto memberDto = memberService.memberDetail(id);

        return memberDto;
    }

    /**
     * 회원 가입
     * @RequestBody = json 으로 받아볼게 ?
     */
    @PostMapping("/member/create")
    public String createMemberPost(@RequestBody MemberReqDto dto) {

        try {
            memberService.memberCreate(dto);
            return "ok"; // 잘 등록됐다고 ok 출력
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "error 발생 ‼️";
        }
    }

    /**
     * 수정 - 2가지 요청 방식
     * (1) put : 덮어쓰기
     * (2) patch : 부분 수정
     */
    @PatchMapping("/member/pw/update")
    public String pwUpdate(@RequestBody MemberUpdateDto dto) {

        memberService.pwUpdate(dto);
        return "ok";
    }

    /**
     * 삭제
     */
    @DeleteMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.delete(id);

        return "ok";
    }



//    /**
//     * 홈 가기
//     */
//    @GetMapping("/")
//    public String home() {
//        return "member/home";
//    }
}
