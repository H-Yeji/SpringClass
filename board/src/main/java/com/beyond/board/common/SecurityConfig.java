package com.beyond.board.common;

import com.beyond.board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security 설정을 customizing하기 위함
@EnableGlobalMethodSecurity(prePostEnabled = true) // 인증 검사를 pre 시점에 하는 것 (post:사후)
public class SecurityConfig {

    @Bean
    // SecurityFilterChain의 구현체 HttpSercurity
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                // csrf 공격에 대한 설정을 하지 않겠다 !
                .csrf()
                .disable()
                .authorizeRequests()
                    // antMathches : 포함되면 인증 제외 => 홈, author/create, login screen페이지는 인증할 필요 없음
                    .antMatchers("/", "/author/create", "/author/login-screen")
                    .permitAll()
                    // 그외 요청은 모두 인증 필요
                    .anyRequest().authenticated()
                .and()
                // 만약 세션 로그인이 아니라, 토큰 로그인일 경우 (나중에 할 것)
//                .sessionManagement().sessionCreationPolicy(SessionCreatePolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-screen") // 내가 만든 로그인 페이지로 가게따
                    // doLogin 메서드는 스프링에서 미리 구현되어 있음
                    .loginProcessingUrl("/doLogin")
                        // 다만, doLogin에 넘겨줄 email, password 속성명은 별도 지정
                        .usernameParameter("email")
                        .passwordParameter("password")
                // 성공하면 LoginSuccessHandler로 가랏
                .successHandler(new LoginSuccessHandler())
                .and()
                    .logout()
                    // 시큐리티에서 구현된 doLogout 기능 그대로 사용
                    .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
