package com.beyond.board.post.service;

import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostCreateDto;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    @Autowired
    public PostService (PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 게시글 등록
     */
    @Transactional
    public void registPost(PostCreateDto dto) {

        Post post = dto.toEntity(); // dto > entity
        postRepository.save(post);
    }

    /**
     * 게시글 목록 조회
     */
    public List<PostResDto> postList() {

        List<Post> postList = postRepository.findAll();

        List<PostResDto> postResDtoList = new ArrayList<>();

        for (Post post : postList) {
            PostResDto postResDto = post.listFromEntity();
            postResDtoList.add(postResDto);
        }

        return postResDtoList;
    }

    /**
     * 게시글 상세 조회
     */
    public PostDetailDto postDetail(Long id) {

        Optional<Post> findPost = postRepository.findById(id);

        Post post = findPost.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        PostDetailDto postDetailDto = post.detailFromEntity();

        return postDetailDto;
    }

}
