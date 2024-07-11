package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository // 나 repository얌 + 싱글톤으로 생성해줄게
public class MemberMemoryRepository implements MemberRepository{

    private final List<Member> memberList;

    MemberMemoryRepository() {
        this.memberList = new ArrayList<>();
    }

    @Override
    public Member save(Member member) {
        memberList.add(member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }
}
