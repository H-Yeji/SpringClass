package com.beyond.board.post.domain;

import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public PostResDto listFromEntity() {

        LocalDateTime createdTime = this.getCreatedTime();
        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        PostResDto postResDto = new PostResDto(this.id, this.title, date);
        return postResDto;
    }

    public PostDetailDto detailFromEntity() {

        LocalDateTime createdTime = this.getCreatedTime();
        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        PostDetailDto postDetailDto = new PostDetailDto(this.id, this.title, this.contents, date);
        return postDetailDto;
    }

}
