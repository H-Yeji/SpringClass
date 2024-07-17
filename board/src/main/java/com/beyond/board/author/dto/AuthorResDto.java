package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResDto {

    private Long id;
    private String name;
    private String email;
    private Role role;
}
