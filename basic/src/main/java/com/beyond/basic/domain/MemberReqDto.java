package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReqDto {

    private String name;
    private String email;
    private String password;

    // dto에서 entity로 변환 (메서드에서 중복 코드 제거하기 위해)
    // 추후에는 builder 패턴으로 변환할 것
    public Member toEntity() {
//        Member member = new Member();
//        member.setName(this.name);
//        member.setEmail(this.email);
//        member.setPassword(this.password);
        Member member = new Member(this.name, this.email, this.password);

        return member;
    }
}
