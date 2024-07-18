package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorCreatedDto;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 생성
     */
    @GetMapping("/create")
    public String createAuthor() {
        log.info("화면 열어보기");
        return "author/author_register";
    }

    @PostMapping("/create")
    public String createAuthor(AuthorCreatedDto dto) {
        log.info("등록해보까");
        authorService.authorCreate(dto);
        System.out.println("등록완");
        return "redirect:/author/list";
    }

    /**
     * 목록 조회
     */
    @GetMapping("/list")
    public String authorList(Model model) {
        List<AuthorResDto> authorResDtoList = authorService.authorList();
        model.addAttribute("authorList", authorResDtoList);
        return "author/author_list";
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {

        AuthorDetailDto authorDetailDto = authorService.authorDetail(id);
        model.addAttribute("author", authorDetailDto);

        return "author/author_details";
    }


}
