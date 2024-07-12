package com.beyond.basic.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
// entity 어노테이션을 통해 엔티티 매니저에게 객체 관리를 위임 (jpa 추가하면 사용가능)
// 해당 클래스명으로 테이블 및 컬럼을 자동 생성하고, 각종 설정 정보를 위임
public class Member {

    // IDENTITY : auto_increment 설정
    // auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는 것
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // long은 bigint로 변환

    // String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환
    private String name;

    @Column(nullable = false, length = 50, unique = true) // nullable=false : not null 제약조건
    private String email;

    //@Column(name = "pw") // 이렇게 할 순 있는데 안쓰는게 좋음 (컬럼명이랑 name이랑 일치시키는게 좋음)
    private String password;


}
