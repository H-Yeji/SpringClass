package com.beyond.board.post.service;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * batch를 돌리기 위한 스케줄러 정의
 */
@Component
public class PostBatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private PostJobConfiguration postJobConfiguration;

    @Scheduled(cron = "0 0/1 * * * *")
    public void batchSchedule() {

        Map<String, JobParameter> configMap = new HashMap<>();
        configMap.put("time", new JobParameter(System.currentTimeMillis())); // 현재 시간을 ms로

        JobParameters jobParameters = new JobParameters(configMap); // 현재 시간 넣음

        try {
            this.jobLauncher.run(postJobConfiguration.executeJob(), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
