package com.beyond.basic.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // fk 설정
    // 1:1의 경우 OneToOne을 설정하고, unique=true로 설정
    // 1:N, N:1 설정
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // jpa의 영속성(persistence) 컨텍스트에 의해 Member 객체가 관리됨


    public PostResDto listFromEntity() {
        PostResDto postResDto = new PostResDto(this.id, this.title);
        return postResDto;
    }
}
