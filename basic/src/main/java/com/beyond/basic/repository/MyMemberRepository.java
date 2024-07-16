package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 직접 구현할 수 있음 -> 런타임 시점에서 만들어짐

    List<Member> findByName(String name);

    /**
     * spring data jpa는 아래 코드에서 jpql 문법 에러가 났을 때
     * 컴파일 타임에서 오류를 체크해줌
     * (ex. 아래 jpql raw 쿼리에서 s가 빠질경우
     */
//    @Query("select m from Member m")
//    List<Member> myFindAll();

}