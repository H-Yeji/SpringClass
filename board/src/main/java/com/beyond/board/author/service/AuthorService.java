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
    public void authorCreate(AuthorCreatedDto dto) {

        Author author = dto.toEntity(); // dto > entity
        authorRepository.save(author);
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

}
