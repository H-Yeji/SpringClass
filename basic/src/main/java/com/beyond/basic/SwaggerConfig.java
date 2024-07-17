package com.beyond.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 이건 외부 라이브러리를 가져다가 싱글톤 객체를 만드는거라, configuration-bean으로 사용 (component가 아니라)
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    // Docket : Swagger 구성의 핵심 기능 클래스
    // Docket을 리턴함으로써 싱글톤 객체로 생성
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 어떤 컨트롤러 또는 어떤 api를 swagger 문서에 포함시킬 지 선택
                .select()
                // 모든 requestHandler(controller)를 문서화 대상으로 선택한다는 설정
                .apis(RequestHandlerSelectors.any())
                // 특정 path만 문서화 대상으로 하겠다라는 설정
                // * : 보통은 바로 아래 경로까지만 사용
                // ** : 자손까지 포함
                .paths(PathSelectors.ant("/rest/**")) // rest로 시작하는 컨트롤러 사용
                .build();

    }
}
