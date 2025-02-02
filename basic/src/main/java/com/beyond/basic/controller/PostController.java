package com.beyond.basic.controller;

import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import com.beyond.basic.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * 게시글 목록 조회
     */
    @GetMapping("/post/list")
    @ResponseBody
    public List<PostResDto> postList() {
        List<PostResDto> postResDtoList = postService.postList();
        return postResDtoList;
    }

    /**
     * lazy(지연로딩), eager(즉시로딩) 테스트
     */
    @GetMapping("post/member/all")
    @ResponseBody
    public void memberPostAll() {
        System.out.println("postRepository: " + postRepository.findAll());
    }
}
