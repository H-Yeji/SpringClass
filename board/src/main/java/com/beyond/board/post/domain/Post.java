package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.PostDetailDto;
import com.beyond.board.post.dto.PostResDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000)
    private String contents;

    // author와 관계설정
    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

//    @CreationTimestamp
//    private LocalDateTime createdTime;
//    @UpdateTimestamp
//    private LocalDateTime updateTime;

//    public Post(String title, String contents, Author author) {
//        this.title = title;
//        this.contents = contents;
//        this.author = author;
//    }

    public PostResDto listFromEntity(Author author) {

        LocalDateTime createdTime = this.getCreatedTime();
        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        //PostResDto postResDto = new PostResDto(this.id, this.title, date);
        PostResDto postResDto = PostResDto.builder()
                .id(this.id)
                .title(this.title)
                //.author(author) // 스택오버플로우 에러 (순환참조 에러 확인)
                .author_email(author.getEmail())
                .createdTime(date)
                .build();
        return postResDto;
    }

    public PostDetailDto detailFromEntity() {

        // create 시간
        LocalDateTime createdTime = this.getCreatedTime();
        String date1 = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        // update 시간
//        LocalDateTime updatedTime = this.getUpdatedTime();
//        String date2 = updatedTime.getYear()+"년 " + updatedTime.getMonthValue() + "월 " +
//                updatedTime.getDayOfMonth() + "일";

        //PostDetailDto postDetailDto = new PostDetailDto(this.id, this.title, this.contents, date);
        PostDetailDto postDetailDto = PostDetailDto.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .createdTime(date1)
                //.updatedTime(date2)
                .build();

        return postDetailDto;
    }

}
