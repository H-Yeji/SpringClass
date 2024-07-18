package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostCreateDto;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import com.beyond.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @PostMapping("/create")
    public String registPost(@RequestBody PostCreateDto dto) {

        log.info("regis들어간다");
        postService.registPost(dto);
        log.info("regist에서 나왔다");

        return "ok";
    }

    /**
     * 게시물 목록 조회
     */
    @GetMapping("/list")
    public List<PostResDto> postList() {

        List<PostResDto> postResDto = postService.postList();

        return postResDto;
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/detail/{id}")
    public PostDetailDto detailPost(@PathVariable Long id) {

        PostDetailDto postDetailDto = postService.postDetail(id);

        return postDetailDto;
    }
}
