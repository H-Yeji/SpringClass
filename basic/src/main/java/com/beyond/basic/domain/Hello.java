package com.beyond.basic.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

//@Getter @Setter // 롬복 라이브러리를 통해 getter/setter 어노테이션 사용
@Data // getter, setter, toString까지 구현
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 사용한 생성자 구현
public class Hello {

    private String name;
    private String email;
    private String password;
    //private MultipartFile file;

//    @Override
//    public String toString() { // -> Data 어노테이션으로 대체 가넝
//        return "[name]: " + name + " [email]: " + email + " [password]: " + password;
//    }

    // builder패턴 직접 구현
    // 빌더 패텬 적용 대상 생성자가 필요
    public Hello(HelloBuilder helloBuilder) {
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    public static HelloBuilder builder() {
        return new HelloBuilder();
    }

    public static class HelloBuilder {

        private String name;
        private String email;
        private String password;

        public HelloBuilder name (String name) {
            this.name = name;
            return this; // 메모리 주소만 return
        }
        public HelloBuilder email (String email) {
            this.email = email;
            return this;
        }
        public HelloBuilder password (String password) {
            this.password = password;
            return this;
        }
        public Hello build() {
            return new Hello(this);
        }
    }
}
