package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorCreatedDto;
import com.beyond.board.author.dto.AuthorDetailDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
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
    public Author authorCreate(AuthorCreatedDto dto) {

        if (authorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧음");
        }
        Author author = dto.toEntity(); // dto > entity

        //======================== casecade persist 테스트 ========================
        // 회원가입하면 게시글 자동 생성
        author.getPosts().add(Post.builder()
                .title("가입인사")
                .author(author)
                .contents("안녕하세요, " + dto.getName() + "입니다.")
                .build());
        //author에 list가 초기화가 안되어있어서 에러남 -> 빌더 패턴으로 해줘야함
        // AuthorCreateDto에 .posts(new ArrayList<>)로 초기화 ㄱㄱ
        // 나중에 "주문 서비스"에서 이런 기능 활용함

        // 여기 save는 꼭 있어야 함 => "추가"하는 과정이기 때문 ⭐
        Author savedAuthor = authorRepository.save(author);

        return savedAuthor;
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
     * 회원 상세 조회-> detail용
     */
    public AuthorDetailDto authorDetail(Long id) {

        Optional<Author> findAuthor = authorRepository.findById(id);

        Author author = findAuthor.orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));

        AuthorDetailDto authorDetailDto = author.detailFromEntity();

        return authorDetailDto;
    }

//    /**
//     * 회원 상세 조회 -> update 용
//     */
//    public AuthorUpdateDto authorUpdateDetail(Long id) {
//
//        Optional<Author> findAuthor = authorRepository.findById(id);
//
//        Author author = findAuthor.orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));
//
//        AuthorUpdateDto authorUpdateDto = author.updateFromDetail();
//
//        return authorUpdateDto;
//    }

    // email로 author 객체 찾아오기
    public Author authorFindByEmail(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("없는 이메일입니다."));

        return author;
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public void authorUpdate(Long id, AuthorUpdateDto dto) {

        Optional<Author> findAuthor = authorRepository.findById(id);

        Author author = findAuthor.orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));

        author.updateFromEntity(dto);

        // ❓update에서 save를 무조건 해야하나 ? => 아뉩
        // jpa가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 db에 반영 => dirty checking(변경 감지)
        // "변경"이기 때문에 save가 필수 아님 ⭐ (dirty checking시, transactional 어노테이션 필요)
        // authorRepository.save(author);

    }
}
