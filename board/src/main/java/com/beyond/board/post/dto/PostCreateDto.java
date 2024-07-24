package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private String title;
    private String contents;
    private Long authorId;
    // 추후 로그인 기능 이후에는 없어질 dto -> session으로 바꿈
    //private String authorEmail;
    //private Author author; // 순환참조 테스트 확인용
    private String appointment;
    private String appointmentTime;


    public Post toEntity(Author author, LocalDateTime appointmentTime) {
        //Post post = new Post(this.title, this.contents, this.author);

        Post post = Post.builder()
                .title(this.title)
                .contents(this.contents)
                .appointment(this.appointment)
                .appointmentTime(appointmentTime)
                .author(author)
                .build();
        return post;
    }
}
