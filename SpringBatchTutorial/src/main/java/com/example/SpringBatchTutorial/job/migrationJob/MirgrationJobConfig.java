package com.example.SpringBatchTutorial.job.migrationJob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MirgrationJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job migrationJob(Step migrationStep) {
		return jobBuilderFactory.get("migrationJob")
				.incrementer(new RunIdIncrementer())
//				.listener(new JobLoggerListener())
				.start(migrationStep)
				.build();
	}
	
	@JobScope
	@Bean
	public Step migrationStep(Tasklet migrationTasklet) {
		return stepBuilderFactory.get("migrationStep")
				.tasklet(migrationTasklet)
				.build();
	}
	
	@StepScope
	@Bean
	public Tasklet jobListenerTasklet() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				throw new Exception("Failed!!");
			}
		};
	}

}
