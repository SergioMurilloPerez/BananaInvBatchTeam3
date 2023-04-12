package es.netmind.banana_invoices.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class LoggingJobStartStopListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
		System.out.println("LoggingJobStartStopListener: "+jobExecution.getJobConfigurationName() + " has begun!");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
		System.out.println("LoggingJobStartStopListener: "+jobExecution.getJobConfigurationName() + " has ended!");
    }
}
