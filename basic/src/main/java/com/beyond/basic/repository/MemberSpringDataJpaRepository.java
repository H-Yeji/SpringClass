package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// spring data jpa가 repository가 되기 위해선 jpaRepository를 상속해야하고,
// <Entity명, pk타입>으로 작성

// MemberSpringDataJpaRepository는 jpaRepository를 상속받아서
// => jpaRepository의 주요 기능을 상속함 (타입이랑 메서드명 다 이미 지정되어있음)
// JpaRepository 인터페이스 상속받고 있는데 ㅇㅒ 구현체 어디있어 !? => 그거시 바러 Hibernate ~
// Hibernate 구현체가 우리 눈에 안보이지만 어딘가에서 이미 구현하고 있음 -> 그 기능을 우리 코드에 주입하는 시점: 런타임 시점⭐
// 런타임 시점에 사용자가 인터페이스에 정의한 메서드를 reflection 기술을 통해 메서드 구현
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 직접 구현할 수 있음 -> 런타임 시점에서 만들어짐

}
