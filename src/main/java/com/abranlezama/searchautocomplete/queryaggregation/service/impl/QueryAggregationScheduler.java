package com.abranlezama.searchautocomplete.queryaggregation.service.impl;

import com.abranlezama.searchautocomplete.queryaggregation.service.IQueryAggregationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

@Component
@ConditionalOnProperty(name = "batch.job.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class QueryAggregationScheduler implements IQueryAggregationScheduler {

    private final JobLauncher jobLauncher;
    private final Job queryLogAggregationJob;

    @Override
    @Scheduled(cron = "0 0 0 * * MON")
    public void runBatchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        LocalDate weekOf = Instant.now().atZone(ZoneId.of("America/Los_Angeles")).toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("weekOf", weekOf)
                .toJobParameters();

        jobLauncher.run(queryLogAggregationJob, jobParameters);
    }
}
