package com.beyond.board.post.controller;

import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostCreateDto;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * 게시물 목록 조회 -> page 처리로 변경
     */
    @GetMapping("/list")
    public String postList(Model model, @PageableDefault(size=10, sort = "createdTime"
            , direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PostResDto> postResDto = postService.postList(pageable);
        model.addAttribute("postList", postResDto);

        return "post/post_list";
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/detail/{id}")
    public String detailPost(@PathVariable Long id, Model model) {

//        log.info("get요청이고, parameter는 " + id);
//        log.info("method 명 : detailPost");

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

    /**
     * post 목록 페이징 처리 테스트
     */
    @GetMapping("/list/page")
    @ResponseBody
    // Pageable 요청 방법 : localhost:8080/post/list?size=10&page=0 (쿼리 파라미터 방식)
    public Page<PostResDto> postListPage(@PageableDefault(size=10, sort = "createdTime"
            , direction = Sort.Direction.DESC) Pageable pageable) { // Page를 리턴할 때 타입에 List<> 빼도됨

        return postService.postListPage(pageable);
    }


}
