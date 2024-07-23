package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorCreatedDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@Transactional // 테스트이기 때문에 rollback이 되어야 함 (값이 저장되면 안됨)
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    // 저장 및 detail 조회
    @Test
    void saveAndFind() {
        AuthorCreatedDto authorCreatedDto = new AuthorCreatedDto("yeji2", "yeji2@test.com", "21341234", Role.ADMIN);
        Author author = authorService.authorCreate(authorCreatedDto); // save 테스트

        Author authorDetail = authorService.authorFindByEmail("yeji2@test.com"); // email로 조회

        // 검증
        Assertions.assertEquals(author.getEmail(), authorCreatedDto.getEmail());
        Assertions.assertEquals(authorDetail.getEmail(), authorCreatedDto.getEmail());
    }

    // update
    @Test
    void update() {

        // 객체 create
        AuthorCreatedDto authorCreatedDto = new AuthorCreatedDto("yeji3", "yeji3@test.com", "21341234", Role.ADMIN);
        Author author = authorService.authorCreate(authorCreatedDto); // save 테스트

        // 수정 작업 (name, password)
        // 이름과 비밀번호 변경
        AuthorUpdateDto authorUpdateDto = AuthorUpdateDto.builder()
                .name("yeji4")
                .password("454545454545445")
                .build();
        authorService.authorUpdate(author.getId(), authorUpdateDto);


        // 수정 후 재조회 : name, password가 맞는지 각각 검증
        // 이메일로 찾아온 author의 변경한 name과 입력받은 dto.name이 같은지 검증
        Author savedAuthor = authorService.authorFindByEmail("yeji3@test.com");
        Author savedAuthor2 = authorRepository.findById(author.getId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원"));

        Assertions.assertEquals(savedAuthor.getName(), authorUpdateDto.getName());
        Assertions.assertEquals(savedAuthor.getPassword(), authorUpdateDto.getPassword());


//        Assertions.assertEquals(author.getName(), authorUpdateDto.getName());
//        Assertions.assertEquals(author.getPassword(), authorUpdateDto.getPassword());
    }

    // findAll
    @Test
    void findAll() {

        // 미리 기존의 목록 개수 구하기
        int size = authorService.authorList().size();

        // 3개 author 객체 저장
        AuthorCreatedDto authorCreatedDto1 = new AuthorCreatedDto("yeji5", "yeji5@test.com", "21341234", Role.ADMIN);
        Author author1 = authorService.authorCreate(authorCreatedDto1);
        AuthorCreatedDto authorCreatedDto2 = new AuthorCreatedDto("yeji6", "yeji6@test.com", "21341234", Role.USER);
        Author author2 = authorService.authorCreate(authorCreatedDto2);
        AuthorCreatedDto authorCreatedDto3 = new AuthorCreatedDto("yeji7", "yeji7test.com", "21341234", Role.USER);
        Author author3 = authorService.authorCreate(authorCreatedDto3);

        // authorList 조회 한 후 저장한 개수와 저장된 개수 비교
        List<AuthorResDto> authorResDtos = authorService.authorList(); // 목록

        Assertions.assertEquals(authorResDtos.size(), size + 3);
    }
}
