package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // lazy
    // jpql을 적용하여 네이밍 룰을 통한 방식이 아닌 메서드 생성
    // 나가는 쿼리 : select a.*, p.* from post p left join author a on p.author_id=a.id;
    // 둘 다 다 가지고옴 a, p
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetch();

    // ❓ 그냥 left join이랑 차이가 뭔가
    // 나가는 쿼리 : select p.* from post p left join author a on p.author_id = a.id;
    // a를 가져오지 않음 => 안가져오는데 join 왜함 ?
    // : 아래의 join문은 author 객체를 통한 조건문으로 post를 filtering 할 때 사용
    // n+1 이슈가 다시 발생함. 왜? a를 가져오는거까진 괜춘, 근데 a를 참조해버리게 되면 다시 n+1 이슈가 발생하는 것
    @Query("select p from Post p left join p.author")
    List<Post> findAll();
}
