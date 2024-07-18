package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorResDto {

    private Long id;
    private String name;
    private String email;
    private Role role;
}
