package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 화면을 출력하는 것이 아니라 json 데이터를 전송하기
 */
@RestController //@ResponseBody + @Controller
@RequestMapping("/rest")
@Api(tags = "회원관리 서비스")
public class MemberRestController {

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService; // 이름이 같아서 (다형성x) this 사용
    }


    /**
     * 회원 목록 조회
     */
//    @GetMapping("/member/list")
//    public List<MemberResDto> findMemberList() {
//
//        List<MemberResDto> memberResDtoList = memberService.memberList();
//
//        return memberResDtoList;
//    }

    // memberList : CommonResDto 감싸서 상태코드까지 return 200
    @GetMapping("/member/list")
    public ResponseEntity<List<CommonResDto>> findMemberList() {

        List<MemberResDto> memberResDtoList = memberService.memberList();

        List<CommonResDto> commonResDto = new ArrayList<>();
        for (MemberResDto memberResDto: memberResDtoList) {
            commonResDto.add(new CommonResDto(HttpStatus.OK, "no error", memberResDto));
        }
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    /**
     * 회원 상세 조회
     */
//    @GetMapping("/member/detail/{id}")
//    public MemberDetailResDto memberDetail(@PathVariable Long id) {
//
//        MemberDetailResDto memberDto = memberService.memberDetail(id);
//        System.out.println(memberDto);
//
//        return memberDto;
//    }

    // member/detail : 성공하면 CommonResDto 200, 예외터지면 CommonErrorDto로 return 404
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id) {

        try {
            MemberDetailResDto memberDto = memberService.memberDetail(id);

            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "no error", memberDto);

            return new ResponseEntity<>(commonResDto, HttpStatus.OK);

        } catch (EntityNotFoundException e){
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());

            return new ResponseEntity<>(commonErrorDto, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 회원 가입
     * @RequestBody = json 으로 받아볼게 ?
     */
//    @PostMapping("/member/create")
//    public String createMemberPost(@RequestBody MemberReqDto dto) {
//
//        try {
//            memberService.memberCreate(dto);
//            return "ok"; // 잘 등록됐다고 ok 출력
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return "error 발생 ‼️";
//        }
//    }

    // member/create : 성공하면 200, 실패하면 400
    @PostMapping("/member/create")
    public ResponseEntity<?> createMemberPost(@RequestBody MemberReqDto dto) {

        try {
            memberService.memberCreate(dto);

            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member created successfully", null);
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
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

    @GetMapping("/member/text")
    public String memberText() {
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
