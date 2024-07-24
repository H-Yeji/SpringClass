//package com.beyond.board.author;
//
//
//import com.beyond.board.author.controller.AuthorController;
//import com.beyond.board.author.domain.Author;
//import com.beyond.board.author.domain.Role;
//import com.beyond.board.author.dto.AuthorCreatedDto;
//import com.beyond.board.author.dto.AuthorDetailDto;
//import com.beyond.board.author.dto.AuthorUpdateDto;
//import com.beyond.board.author.repository.AuthorRepository;
//import com.beyond.board.author.service.AuthorService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.Optional;
//
//@SpringBootTest
//@Transactional
//public class AuthorServiceMockTest {
//
//    @Autowired
//    private AuthorService authorService;
//
//    // 가짜 객체를 만드는 작업을 목킹이라 함 (가짜 repo 생성해서 사용)
//    @MockBean
//    private AuthorRepository authorRepository;
////    @Autowired
////    private AuthorRepository authorRepository;
//
//    // 저장 및 detail 조회
//    @Test
//    public void findAuthorDetailTest() {
//
//        // 가짜 객체 만들기
//        AuthorCreatedDto authorCreatedDto = new AuthorCreatedDto("test1", "test1@test.com", "21341234", Role.ADMIN);
//        Author author = authorService.authorCreate(authorCreatedDto); // save 테스트
//
//        AuthorDetailDto authorDetailDto = authorService.authorDetail(author.getId());
//
//        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
//
//        // dto의 email과 'db에 있는 1번 email'을 비교할건데 -> 우린 db를 가져오는게 목적이 아니라
//        // authorDetail의 return이 잘 되었는지가 목적임 = 즉, 가짜를 만들어서 비교하면 됨
//
//        // 기존 방법으로 했을 때  => id로 찾아와서 비교
//        //Author author1 = authorRepository.findById(author.getId()).orElseThrow(() -> new EntityNotFoundException("없는 회원"));
//
//        // mock 활용
//        Author savedAuthor = authorRepository.findById(author.getId()).orElseThrow(() -> new EntityNotFoundException("없음"));
//        Assertions.assertEquals(authorDetailDto.getEmail(), savedAuthor.getEmail());
//    }
//
//    // update
//
//
//    // findAll
//
//}
