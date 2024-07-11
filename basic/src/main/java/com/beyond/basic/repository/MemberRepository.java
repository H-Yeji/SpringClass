package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public interface MemberRepository {

    /**
     * 저장
     */
    Member save(Member member);

    /**
     * id로 조회
     */
    Member findById(Long id);

    /**
     * 회원 전체 조회
     */
    List<Member> findAll();

    /**
     * id로 삭제
     */
    //Member delete(Long id);
}
