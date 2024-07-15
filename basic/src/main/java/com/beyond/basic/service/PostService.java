package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    @Autowired // 생성자가 하나만 있을 땐 Autowired 생략할 수 있음
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    /**
     * 게시글 목록 조회
     */
    public List<PostResDto> postList() {

        List<Post> findPostList = postRepository.findAll();
        List<PostResDto> postResDtoList = new ArrayList<>();

        for (Post post : findPostList) {
            PostResDto postResDto = post.listFromEntity();
            postResDtoList.add(postResDto);

            System.out.println("저자의 이름은 " + post.getMember().getEmail());
        }
        return postResDtoList;
    }

}
