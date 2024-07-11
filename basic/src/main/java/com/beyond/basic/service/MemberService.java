package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberJdbcRepository;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// input값의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행
@Service // 난 서비스얌 + 싱글톤 객체로 생성해줄게
//@RequiredArgsConstructor // 생성자(public MemberService) 자동 생성
public class MemberService {

    // memberRepository 재할당 불가 -> final
    private final MemberRepository memberRepository;

    // memberService가 호출됨고ㅏ 동시에 memberRepository 생성이 아니라,
    // memberService 생성자가 호출될 때 memberRepository를 만들기 위해 생성자 생성
//    @Autowired // 싱글톤 객체 주입 (DI) 받음
//    public MemberService(MemberMemoryRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }
    @Autowired // MemberJdbcRepository만 갈아끼워주면 됨 (지금 jdbc 사용하는 중)
    public MemberService(MemberJdbcRepository memberMemoryRepository) {
        this.memberRepository = memberMemoryRepository;
    }


    // service에서 controller 참조 -> 순환 참조 -> 실행이 안될 것
//    @Autowired
//    private MemberController memberController;

    /**
     * 회원 가입
     */
    public void memberCreate(MemberReqDto dto) {

        // 왼쪽 인터페이스, 오른쪽 구현체
        //MemberRepository memberRepository = new MemberMemoryRepository();

        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 넘 짧음‼️");
        }
        // dto를 Member 객체로 변환해서 db(repository)에 넘겨줌
        // 왜냐면 dto는 객체가 아니기 때문
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
    }

    /**
     * 회원 상세 조회
     */
    public MemberResDto memberDetail(Long id) {

        Member findMember = memberRepository.findById(id);

        MemberResDto dto = new MemberResDto();
        dto.setName(findMember.getName());
        dto.setEmail(findMember.getEmail());

        return dto;
    }

    /**
     * 회원 목록 조회
     */
    public List<MemberResDto> memberList() {

        List<Member> memberList = memberRepository.findAll();

        List<MemberResDto> memberResDtoList = new ArrayList<>();
        int j = 0;
        for (Member member: memberList) {
            memberResDtoList.add(new MemberResDto());
            memberResDtoList.get(j).setName(member.getName());
            memberResDtoList.get(j).setEmail(member.getEmail());
            j++;
        }

        return memberResDtoList;
    }
}
