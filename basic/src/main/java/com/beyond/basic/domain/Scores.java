package com.beyond.basic.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString까지 구현
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 사용한 생성자 구현
public class Scores {

    private String className;
    private String point;
}
