package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 직접 구현할 수 있음 -> 런타임 시점에서 만들어짐

}