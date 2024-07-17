package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AuthorResDto listFromEntity() {

        AuthorResDto authorResDto = new AuthorResDto(this.id, this.name, this.email, this.role);

        return authorResDto;
    }

    public AuthorDetailDto detailFromEntity() {

        LocalDateTime createdTime = this.getCreatedTime();
        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        AuthorDetailDto authorDetailDto = new AuthorDetailDto(this.id, this.name, this.email, this.password, date);
        return authorDetailDto;
    }

}
