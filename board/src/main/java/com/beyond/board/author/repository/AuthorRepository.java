package com.beyond.board.author.repository;

import com.beyond.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // findBy컬럼명의 규칙으로 자동으로 where 조건문을 사용한 쿼리 생성
    // 그 외 : findByNameAndEmail, findByNameOrEmail
    // findByAgeBetween(int start, int end)
    // findByAgeLessThan(int age), findByAgeGraterThan(int age)
    // findByAgeIstNull, findByIsNotNull
    // finAllOrderByEmail : 정렬조건 추가
    Optional<Author> findByEmail(String email);

}
