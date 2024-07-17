package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// abstract class : 객체를 만들 수 없는 클래스로 생성
@Getter
@MappedSuperclass // 기본적으로 entity는 상속관계 불가능 -> 이 어노테이션 붙여야 상속관계 성립가능
public abstract class BaseEntity {

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
