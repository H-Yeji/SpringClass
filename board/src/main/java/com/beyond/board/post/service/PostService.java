package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostCreateDto;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    /**
     *  여기서 AuthorService를 주입하면 단점 -> 만약 postService에서도 authorService가 필요할 때 : 순환참조에 걸릴 수 있음
     *  그런 경우는 PostService에서도 직접 "AuthorRepository"를 주입해서 사용하는 것이 좋음
     *
     *  반면에 AuthorRepository 주입하면 단점 -> AuthorService에 findByEmail로 찾아오는 메서드 생성해서 내부에서 예외 처리까지 하면 되는데,
     *  근데 AuthorRepository를 주입하면 email이나 id로 찾아오고, 예외처리하는 코드까지 계속 중복 작성해야함
     *  => 이 정도는 괜찮으나, 추후에 코드가 길어져 중복 코드가 많아지면 Service를 주입하는 방법 고려
     */
    private final PostRepository postRepository;
    private final AuthorService authorService;
    //private final AuthorRepository authorRepository;
    @Autowired
    public PostService (PostRepository postRepository, AuthorService authorService) {
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    /**
     * 게시글 등록
     */
    @Transactional
    public Post registPost(PostCreateDto dto) {

        // (1) authorRepository 주입받아서 -> id로 찾기
//        Long findId = dto.getAuthorId();
//        log.info("id = {}", findId);
//
//        Optional<Author> findAuthor = authorRepository.findById(findId);
//        log.info("findAuthor = {}", findAuthor);
//
//        Author author = findAuthor.orElseThrow(() -> new IllegalArgumentException("없는 id입니다"));
//        log.info("author = {}", author);

        // (2) authorService 주입받아서 -> email로 찾기
//        String findEmail = dto.getAuthorEmail();
//        log.info("이메일 잘 찾았음? {}", findEmail);
//        Author author = authorService.authorFindByEmail(findEmail);
//        log.info("이메일로 찾아온 객체 : {}", author.getEmail());

        // (3) 로그인 세션으로 이메일 찾아오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorService.authorFindByEmail(email);
        // 세션에서 email 꺼내와
//        HttpSession httpSession = request.getSession(); // 사용자의 session꺼내기
//        httpSession.setAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());

        //Post post = dto.toEntity(author, dto.getAppointment_yn());
        //return postRepository.save(post);

        LocalDateTime appointmentTime = null;
        if (dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();

            if (appointmentTime.isBefore(now)) {
                throw new IllegalArgumentException("시간 입력이 잘못되었습니다.");
            }
        }

        Post post = dto.toEntity(author, appointmentTime);
        return postRepository.save(post);

    }

    /**
     * 게시글 목록 조회
     */
    public Page<PostResDto> postList(Pageable pageable) {

        //List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllFetch(); // lazy 때문에 변경
//
//        List<PostResDto> postResDtoList = new ArrayList<>();
//
//        for (Post post : postList) {
//
//            // lazy 때문에 변경
//            // 작성자 이메일도 가져와서 보내기
////            String findEmail = post.getAuthor().getEmail();
////            Author author = authorService.authorFindByEmail(findEmail);
//
////            PostResDto postResDto = post.listFromEntity(author);
//            PostResDto postResDto = post.listFromEntity();
//            postResDtoList.add(postResDto);
//        }
//
//        return postResDtoList;

//        Page<Post> posts = postRepository.findAll(pageable);
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostResDto> postResDtos = posts.map(a -> a.listFromEntity());

        return postResDtos;
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

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long id) {
        // 세션으로 "로그인한 회원의 게시글"인지 확인해서 삭제
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); // getName이 email가져오는거
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없음"));

        if (!post.getAuthor().getEmail().equals(email)) { // 해당 게시글 작성자와 내가 찾아온 email과 같으면 삭제가능
            throw new IllegalArgumentException("본인의 게시글이 아님");
        }
        postRepository.delete(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void postUpdate(Long id, PostUpdateDto dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 게시글 입니다."));

        if (!post.getAuthor().getEmail().equals(email)) { // 해당 게시글 작성자와 내가 찾아온 email과 같으면 삭제가능
            throw new IllegalArgumentException("본인의 게시글이 아님");
        }
//        Optional<Post> findPost = postRepository.findById(id);
//
//        Post post = findPost.orElseThrow(() -> new EntityNotFoundException("없는 게시글 입니다."));

        post.updateFromEntity(dto);

        postRepository.save(post);

    }

    /**
     * post list 페이징 처리
     */
    public Page<PostResDto> postListPage(Pageable pageable) {
        // jpa의 findAll은 return 타입이 List<Post>이므로 새로 만들어줘야 함
        Page<Post> posts = postRepository.findAll(pageable);

        Page<PostResDto> postResDtos = posts.map(a -> a.listFromEntity());

        return postResDtos;
    }


}
