package com.beyond.basic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//@Data
@Entity
// entity 어노테이션을 통해 엔티티 매니저에게 객체 관리를 위임 (jpa 추가하면 사용가능)
// 해당 클래스명으로 테이블 및 컬럼을 자동 생성하고, 각종 설정 정보를 위임
//@AllArgsConstructor // 모든 생성자
@NoArgsConstructor // 기본 생성자는 jpa에서 필수
@Getter
public class Member {

    // IDENTITY : auto_increment 설정
    // auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // long은 bigint로 변환

    // String은 varchar(255)로 기본으로 변환. name 변수명이 name 컬럼명으로 변환
    private String name;

    @Column(nullable = false, length = 50, unique = true) // nullable=false : not null 제약조건
    private String email;

    //@Column(name = "pw") // 이렇게 할 순 있는데 안쓰는게 좋음 (컬럼명이랑 name이랑 일치시키는게 좋음)
    private String password;

    @OneToMany(mappedBy = "member") // 어디랑 매핑되어있는지
    private List<Post> posts; // post의 개수확인할 때 List형태여야 함 (한명 회원의 post 전체)

    // 시간 추가 (LocalDateTime > db 에선 DateTime으로 들어감)
    // 캐멀케이스 사용시 db에는 언더바로 생성됨 (created_time)
    @CreationTimestamp // db에는 current_timestamp가 생성되지 않음
    // 이 어노테이션 사용 > 직접 db에 insert할 땐 시간이 안찍힘 = 즉, 프로그램을 꼭 타고 데이터가 들어가야 함
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // password 수정하는 코드 추가
    // password 상단에 @Setter를 통해 특정 변수만 setter 사용이 가능하지만
    // 일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것 권장
    public void updatePwd(String password) {
        this.password = password;
    }

    // list entity > dto
    public MemberResDto listFromEntity() {

        MemberResDto memberDto = new MemberResDto(this.id, this.name, this.email);
        return memberDto;
    }

    public MemberDetailResDto detFromEntity() {

        LocalDateTime createdTime = this.getCreatedTime();

        String date = createdTime.getYear()+"년 " + createdTime.getMonthValue() + "월 " +
                createdTime.getDayOfMonth() + "일";

        MemberDetailResDto memberDto = new MemberDetailResDto(this.id, this.name, this.email, this.password, date);
        return memberDto;
    }

}
