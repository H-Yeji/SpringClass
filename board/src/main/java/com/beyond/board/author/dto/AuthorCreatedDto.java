package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorCreatedDto {

    private String name;
    private String email;
    private String password;
    private Role role;

    public Author toEntity() {
        Author author = new Author(this.name, this.email, this.password, this.role);
        return author;
    }
}
