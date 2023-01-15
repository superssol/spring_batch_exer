package com.example.SpringBatchTutorial.job.migrationJob;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.example.SpringBatchTutorial.job.migrationJob.core.domain.accounts.Accounts;
import com.example.SpringBatchTutorial.job.migrationJob.core.domain.accounts.AccountsRepository;
import com.example.SpringBatchTutorial.job.migrationJob.core.domain.orders.Orders;
import com.example.SpringBatchTutorial.job.migrationJob.core.domain.orders.OrdersRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MirgrationJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
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
	public Step migrationStep(ItemReader ordersReader, ItemProcessor ordersProcessor, ItemWriter ordersWriter) {
		return stepBuilderFactory.get("migrationStep")
				.<Orders, Accounts>chunk(5)
				.reader(ordersReader)
				.processor(ordersProcessor)
				.writer(ordersWriter)
				.build();
	}			
	
	
	@StepScope
	@Bean
	public RepositoryItemReader<Orders> ordersReader() {
		return new RepositoryItemReaderBuilder<Orders>()
		.name("ordersReader")
		.repository(ordersRepository)
		.methodName("findAll")
		.pageSize(5)
		.arguments(Arrays.asList())
		.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
		.build();
	}
	
	@StepScope
	@Bean
	public ItemProcessor<Orders, Accounts> orderProcessor() {
		return new ItemProcessor<Orders, Accounts>() {
			@Override
			public Accounts process(Orders item) throws Exception {
				return new Accounts(item);
			}
		};
	}
	
	@StepScope
	@Bean
	public RepositoryItemWriter<Accounts> orderWriter() {
		return new RepositoryItemWriterBuilder<Accounts>()
				.repository(accountsRepository)
				.methodName("save")
				.build();
	}

}
