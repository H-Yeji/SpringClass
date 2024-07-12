package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    /**
     * 저장
     */
    Member save(Member member);

    /**
     * id로 조회
     */
    Optional<Member> findById(Long id);

    /**
     * 회원 전체 조회
     */
    List<Member> findAll();

    /**
     * id로 삭제
     */
    //Member delete(Long id);
}
