package com.beyond.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDetailDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;

}
