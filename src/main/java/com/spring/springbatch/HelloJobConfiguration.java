package com.spring.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor // @NonNull이나 final이 붙은 필드에 대해 생성자를 생성합니다. (의존성 주입)
@Configuration           // 하나의 배치 Job을 정의하고 빈을 설정합니다.
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;   // Job을 생성하는 빌더 팩토리입니다.
    private final StepBuilderFactory stepBuilderFactory; // Step을 생성하는 빌더 팩토리입니다.

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")        // helloJob 이름으로 Job을 생성합니다.
                .start(helloStepStart())
                .next(helloStepNext())
                .build();
    }

    @Bean
    public Step helloStepStart() {
        return stepBuilderFactory.get("helloStepStart") // helloStep 이름으로 Step을 생성합니다.
                // Step안에서 단일 태스크로 수행되는 로직을 구현합니다.
                // return RepeatStatus.FINISHED or null: 1회만 수행합니다. 기본적으로 무한 반복입니다.
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        // Business Logic
                        System.out.println("Hello Spring Batch !!");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step helloStepNext() {
        return stepBuilderFactory.get("helloStepNext")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        // Business Logic
                        System.out.println("Next step !!");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
