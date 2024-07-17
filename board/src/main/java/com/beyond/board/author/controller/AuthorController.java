package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorCreatedDto;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 생성
     */
    @PostMapping("/create")
    public String createAuthor(@RequestBody AuthorCreatedDto dto) {
        authorService.authorCreate(dto);
        return "ok";
    }

    /**
     * 목록 조회
     */
    @GetMapping("/list")
    public List<AuthorResDto> authorList() {
        List<AuthorResDto> authorResDtoList = authorService.authorList();
        return authorResDtoList;
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/detail/{id}")
    public AuthorDetailDto authorDetail(@PathVariable Long id) {

        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);

        return authorDetailDto;
    }


}
