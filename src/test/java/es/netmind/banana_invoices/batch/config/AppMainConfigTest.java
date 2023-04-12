package es.netmind.banana_invoices.batch.config;

import es.netmind.banana_invoices.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("dev")
class AppMainConfigTest {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Test
    public void testLaunchJob() throws Exception {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

            JobParameters jobParameters = new JobParametersBuilder().addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();


            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Exit Status : " + execution.getStatus());
            assertTrue(execution.getStatus() == BatchStatus.COMPLETED, "Exit status should be COMPLETED");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}