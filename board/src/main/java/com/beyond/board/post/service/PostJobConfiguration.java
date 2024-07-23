//package com.beyond.board.post.service;
//
//import com.beyond.board.post.domain.Post;
//import com.beyond.board.post.repository.PostRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//
//
///**
// * batch 작업 목록 정의
// */
//@Configuration
//public class PostJobConfiguration {
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//    @Autowired
//    private PostRepository postRepository;
//
//    public Job executeJob() {
//        return jobBuilderFactory.get("executeJob")
//                .start(firstStep())
//                .next(secondStep())
//                .build(); // 작업이 연쇄적으로 이어짐
//    }
//
//    // scheduler에서 했던 작업 그대로를 batch에서 실행
//    @Bean
//    public Step firstStep() {
//        return stepBuilderFactory.get("firstStep")
//                .tasklet((Contribution, chunkContext) -> {
//                    // 스케줄러에서 코드 그대로 가져옴
//                    System.out.println("===================예약 글쓰기 batch task1 시작===================");
//
//                    Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y"); //y인 것만 가져옴
//                    for (Post p : posts) {
//                        if (p.getAppointmentTime().isBefore(LocalDateTime.now())) {
//                            p.updateAppointment("N");
//                        }
//                    }
//                    System.out.println("===================예약 글쓰기 task1 종료===================");
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//    @Bean
//    public Step secondStep() {
//        return stepBuilderFactory.get("secondStep")
//                .tasklet((Contribution, chunkContext) -> {
//                    // 스케줄러에서 코드 그대로 가져옴
//                    System.out.println("===================예약 글쓰기 batch task2 시작===================");
//                    System.out.println("===================예약 글쓰기 task2 종료===================");
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//}
