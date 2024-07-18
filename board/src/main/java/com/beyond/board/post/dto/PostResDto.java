package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResDto {

    private Long id;
    private String title;
    private String author_email;
    private String createdTime;
    // 순환참조 테스트 확인용
    //private Author author;

}
