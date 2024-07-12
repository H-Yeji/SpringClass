package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper // build.gradle에 mybatis 관련한 기술 추가 해야함
// @Mapper : 해당 repository에서 mybatis를 쓰겠다는 표현
public interface MemberMybatisRepository extends MemberRepository{ // extends를 해야 서비스에서 의존성 주입 갈아 끼울 수 있음

    // 인터페이스에서 인터페이스를 상속 받을 때 -> extends 사용함
    // 그렇기 때문에 아래 메서드는 작성하지 않아도 됨
//    List<Member> findAll();
//
//    Member save();
//
//    Member findById(Long id);
}
