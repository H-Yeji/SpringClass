package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LoginService implements UserDetailsService {

    @Autowired
    private AuthorService authorService;

    @Override
    // 파라미터 username에 email이 담겨있음 (우리가 email을 받아 로그인함)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Author author = authorService.authorFindByEmail(username);

        // 권한
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole())); // GrantedAuthority의 구현체

        return new User(author.getEmail(), author.getPassword(), authorities);
    }
}
