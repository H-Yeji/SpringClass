package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.CommonResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {

    // case1 : @ResponseStatus 어노테이션 방식
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1() {
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member annotation2() {

        // 객체 생성 후 db저장 성공했다 가정
        Member member = new Member("hello", "hello@test.com", "123123123");

        return member; // 클래스 상단에 responseBody가 있으니까 json으로 데이터 나감
    }

    // case2 메서드 체이닝 방식 : ResponseEntity의 클래스 메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("hello", "hello@test.com", "123123123");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2() {
        Member member = new Member("hello", "hello@test.com", "123123123");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3() {
        return ResponseEntity.notFound().build();
    }

    // case3 : ResponseEntity 객체를 직접 custom하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("hello", "hello@test.com", "123123123");
        return new ResponseEntity<>(member, HttpStatus.CREATED); //이렇게 보내면 header에 들어감 > front개발자가 알아보기 어려움 (개발할 때)
    }

    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2(){
        Member member =new Member("hong", "hong@naver.com", "a1234567890");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,
                "member is successfully created", member); // 담아서
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED); // body에 넣어서 전송

    }


}
