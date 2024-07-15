//package com.beyond.basic.repository;
//
//import com.beyond.basic.domain.Member;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class MemberJdbcRepository implements MemberRepository {
//
//    // Datasource는 db와 jdbc에서 사용하는 db연결 드라이버 객체
//    // application.yml 에서 설정한 db 정보가 자동으로 주입
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public Member save(Member member) {
//        try{
//            Connection connection = dataSource.getConnection();
//            String sql = "insert into member(name, email, password) values(?,?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setString(1, member.getName());
//            preparedStatement.setString(2, member.getEmail());
//            preparedStatement.setString(3, member.getPassword());
//            preparedStatement.executeUpdate(); // 추가, 수정 :  executeUpdate, 조회 : executeQuery
//
//        } catch (SQLException e) {
//            e.printStackTrace(); // 로그로 error 남겨두기
//        }
//        return null;
//    }
//
//    @Override
//    public Optional<Member> findById(Long id) {
//
//        Member member = new Member();
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "select * from member where id=?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, id); // 파라미터로 받아온 id로 세팅
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            resultSet.next();
//            member.setId(resultSet.getLong("id"));
//            member.setName(resultSet.getString("name"));
//            member.setEmail(resultSet.getString("email"));
//            member.setPassword(resultSet.getString("password"));
//            System.out.println(member);
//
//        } catch (SQLException e) {
//            e.printStackTrace();;
//        }
//        return Optional.ofNullable(member);
//    }
//
//    @Override
//    public List<Member> findAll() {
//
//        List<Member> memberList = new ArrayList<>();
//
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "select * from member"; // 목록 전체 조회
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery(); // 쿼리의 반환 타입 : ResultSet
//
//            /**
//             * resultSet의 핵심 동작 원리 : 커서
//             */
//            while(resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String name = resultSet.getString("name");
//                String email = resultSet.getString("email");
//                //String password = resultSet.getString("password");
//
//                Member member = new Member();
//                member.setId(id);
//                member.setName(name);
//                member.setEmail(email);
//                //member.setPassword(password);
//                memberList.add(member);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();;
//        }
//
//        return memberList;
//    }
//}
