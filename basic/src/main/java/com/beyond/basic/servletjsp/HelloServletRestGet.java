package com.beyond.basic.servletjsp;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * controller가 아닌 webServlet을 통해 라우팅을 함
 * 메서드 단위가 아니라 클래스 단위로 url 매핑이 이루어짐
 */
@WebServlet("/hello/servlet/rest/get")
public class HelloServletRestGet extends HttpServlet {

    @Override
    // HttpServletRequest에는 사용자의 "요청 정보"가 담겨있음
    // HttpServletResponse에는 사용자에게 "응답 정보"를 담아줘야 함
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("yeji");
        hello.setEmail("yeji@test.com");
        hello.setPassword("12341234");

        // 응답 객체 설정
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // 직접 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(hello);

        // 응답 주기 (응답 바디 생성)
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        // flush : 기본적으로 버퍼를 통해 데이터가 조립되므로, 버퍼를 비우는 과정
        printWriter.flush();
    }
}
