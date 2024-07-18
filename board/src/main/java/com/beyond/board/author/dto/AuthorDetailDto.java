package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorDetailDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;
    private Role role;
    private Long postCounts;

}
