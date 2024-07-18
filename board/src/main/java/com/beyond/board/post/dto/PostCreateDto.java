package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private String title;
    private String contents;
    private Long authorId;
    // 추후 로그인 기능 이후에는 없어질 dto
    private String authorEmail;
    //private Author author; // 순환참조 테스트 확인용


    public Post toEntity(Author author) {
        //Post post = new Post(this.title, this.contents, this.author);
        Post post = Post.builder()
                .title(this.title)
                .contents(this.contents)
                .author(author)
                .build();
        return post;
    }
}
