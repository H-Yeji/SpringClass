package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreatedDto {

    private String name;
    private String email;
    private String password;
    private Role role;

//    public AuthorCreatedDto(String name, String email, String password, Role role) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }


    public Author toEntity(String encodedPassword) {
        //Author author = new Author(this.name, this.email, this.password, this.role);
        Author author = Author.builder()
                .name(this.name)
                .email(this.email)
                //.password(this.password)
                .password(encodedPassword)
                .role(this.role)
                .posts(new ArrayList<>())
                .build();
        return author;
    }
}
