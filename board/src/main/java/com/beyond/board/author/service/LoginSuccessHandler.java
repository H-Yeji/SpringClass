package com.beyond.board.author.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(); // 사용자의 session꺼내기
        // 방법 (1) : authentication 인증 객체에서 email 정보 추출
//        httpSession.setAttribute("email", authentication.getName()); // 꺼낸 session에 email 세팅해주기(여기서name은 email임)

        // 방법 (2) : SecurityContextHolder 객체에서 authentication 객체를 꺼낸 뒤 email 정보 추출
        httpSession.setAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());

        response.sendRedirect("/");
    }

}
