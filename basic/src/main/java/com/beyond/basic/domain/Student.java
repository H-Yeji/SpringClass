package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // getter, setter, toString까지 구현
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 사용한 생성자 구현
public class Student {

    // 여기 변수명 -> 해당 부분의 자바스크립트랑 변수명이 같아야함
    private String name;
    private String email;
    private List<Scores> grade;
}


