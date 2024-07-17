package com.beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {

    private Long id;
    private String title;
    private String contents;
    private String createdTime;
}
