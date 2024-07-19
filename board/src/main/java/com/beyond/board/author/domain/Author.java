package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
// bulider 어노테이션을 통해 매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅하여 생성자로서 활용
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    // post와 관계설정
    // 일반적으로 부모 엔티티에 cascade 옵션 설정
    // 부모 엔티티 : 자식 객체에게 영향을 끼칠 수 있는 엔티티
    // cascadeType.ALL => 모든 작업을 다 연관시켜서 실행
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;


//    public Author(String name, String email, String password, Role role) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

    public AuthorResDto listFromEntity() {

        //AuthorResDto authorResDto = new AuthorResDto(this.id, this.name, this.email, this.role);
        // builder로 생성
        AuthorResDto authorResDto = AuthorResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .role(this.role)
                .build();

        return authorResDto;
    }

    public AuthorDetailDto detailFromEntity() {

        LocalDateTime createdTime = this.getCreatedTime();
        String date = createdTime.getYear() + "년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        //AuthorDetailDto authorDetailDto = new AuthorDetailDto(this.id, this.name, this.email, this.password, date);
        // builder로 생성
        AuthorDetailDto authorDetailDto = AuthorDetailDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .postCounts((long) this.getPosts().size())
                .createdTime(date)
                .build();

        return authorDetailDto;
    }

    public Author updateFromEntity(AuthorUpdateDto dto) {

        this.name = dto.getName();
        this.password = dto.getPassword();

        return this;
    }

}
