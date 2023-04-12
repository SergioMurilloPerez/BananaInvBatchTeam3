package es.netmind.banana_invoices.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

public class LoggingStepStartStopListener implements StepExecutionListener {
    @BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("LoggingStepStartStopListener: "+stepExecution.getStepName() + " has begun!");
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("LoggingStepStartStopListener: "+stepExecution.getStepName() + " has ended!");
		return stepExecution.getExitStatus();
	}
}
