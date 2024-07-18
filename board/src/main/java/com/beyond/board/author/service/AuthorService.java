package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorCreatedDto;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * 회원가입
     */
    @Transactional
    public Author authorCreate(AuthorCreatedDto dto) {

        if (authorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Author author = dto.toEntity(); // dto > entity
        Author savedAuthor = authorRepository.save(author);

        return savedAuthor;
    }

    /**
     * 목록 조회
     */
    public List<AuthorResDto> authorList() {

        List<Author> authorList = authorRepository.findAll();

        List<AuthorResDto> authorResDtoList = new ArrayList<>();
        for (Author author : authorList) {
            AuthorResDto authorResDto = author.listFromEntity();
            authorResDtoList.add(authorResDto);
        }
        return authorResDtoList;
    }

    /**
     * 회원 상세 조회
     */
    public AuthorDetailDto authorDetail(Long id) {

        Optional<Author> findAuthor = authorRepository.findById(id);

        Author author = findAuthor.orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));

        AuthorDetailDto authorDetailDto = author.detailFromEntity();

        return authorDetailDto;
    }

    // email로 author 객체 찾아오기
    public Author authorFindByEmail(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("없는 이메일입니다."));

        return author;
    }

}
