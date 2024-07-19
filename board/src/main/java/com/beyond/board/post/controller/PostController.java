package com.beyond.board.post.controller;

import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.post.dto.PostCreateDto;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController (PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글 등록
     */
    @GetMapping("/create")
    public String registPost() {
        return "/post/post_register";
    }


    @PostMapping("/create")
    public String registPost(@ModelAttribute PostCreateDto dto) {

        postService.registPost(dto);

        return "redirect:/post/list";
    }

    /**
     * 게시물 목록 조회
     */
    @GetMapping("/list")
    public String postList(Model model) {

        List<PostResDto> postResDto = postService.postList();
        model.addAttribute("postList", postResDto);

        return "post/post_list";
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/detail/{id}")
    public String detailPost(@PathVariable Long id, Model model) {

        PostDetailDto postDetailDto = postService.postDetail(id);
        model.addAttribute("post", postDetailDto);

        return "post/post_details";
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id) {

        postService.delete(id);
        return "redirect:/post/list";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/update/{id}")
    public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateDto postUpdateDto) {

        postService.postUpdate(id, postUpdateDto);

        return "redirect:/post/detail/"+id;
    }
}
