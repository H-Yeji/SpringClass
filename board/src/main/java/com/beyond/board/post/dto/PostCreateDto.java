package com.beyond.board.post.dto;

import com.beyond.board.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private String title;
    private String contents;

    public Post toEntity() {
        Post post = new Post(this.title, this.contents);
        return post;
    }
}
