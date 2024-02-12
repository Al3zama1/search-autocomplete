package com.abranlezama.searchautocomplete.config;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class QueryAggregationJobConfiguration {

    private final ItemReader<QueryLogDTO> queryLogReader;
    private final ItemWriter<QueryLogDTO> queryLogWriter;


    @Bean
    public Step aggregateQuery(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("aggregateQuery", jobRepository)
                .<QueryLogDTO, QueryLogDTO>chunk(10, transactionManager)
                .reader(queryLogReader)
                .writer(queryLogWriter)
                .build();
    }

    @Bean
    public Job queryLogAggregationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("queryLogAggregationJob", jobRepository)
                .start(aggregateQuery(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public DataSource batchDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/job_repository")
                .username("user")
                .password("password")
                .build();
    }

    @Bean
    public JdbcTransactionManager batchTransactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
