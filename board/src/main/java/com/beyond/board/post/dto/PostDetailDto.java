package com.beyond.board.post.dto;

import lombok.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDetailDto {

    private Long id;
    private String title;
    private String contents;
    private String createdTime;
    //private String updatedTime;
}
