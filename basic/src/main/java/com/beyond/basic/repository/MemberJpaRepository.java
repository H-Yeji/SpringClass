package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository {

    // EntityManager => jpa의 핵심 클래스 (객체)
    // entity의 생명주기를 관리하고, 데이터베이스와의 인터페이싱을 책임
    // 즉, 엔티티를 대상으로 crud하는 기능을 제공
    @Autowired
    private EntityManager em;

    @Override
    public Member save(Member member) {
        // persist : 전달된 엔티티 (Member)가 entityManager의 관리상태가 되도록 만들어주고
        // 트랜잭션이 커밋될 때 데이터베이스에 저장 (insert)
        em.persist(member);
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // entityManager를 통해 find
        // find하는데, 매개변수로 pk값 주고, return 타입도 지정해야 함
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);// id줄게, member 클래스를 찾아왕
    }

    // email로 Member 찾아오기
//    public Member findByEmail(String email) {
//        Member member = em.createQuery("select m from Member m where m.email= :email", Member.class)
//                .setParameter("email", email) // :email의 email 부분에 파라미터 email을 가져오기
//                .getSingleResult();
//        // m.email= :email => m.email =?랑 같은 것 (jpql문법)
//        return member;
//    }

    @Override
    public List<Member> findAll() {
        // jpql : jpa의 raw 쿼리 문법 (객체 지향 쿼리 문법)
        // jpa에서는 jpql 문법 컴파일 에러가 나오지 않으나, spring data jpa에서는 컴파일 에러 발생
        List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return memberList;
    }
}
