/*
 * This code is sample code, provided as-is, and we make NO
 * warranties as to its correctness or suitability for any purpose.
 *
 * We hope that it's useful to you. Enjoy.
 * Copyright LearningPatterns Inc.
 */

package es.netmind.banana_invoices.batch.config;

import es.netmind.banana_invoices.batch.processor.ReciboPagadoProcessor;
import es.netmind.banana_invoices.batch.processor.ReciboValidoProcessor;
import es.netmind.banana_invoices.batch.processor.SimpleProcessor;
import es.netmind.banana_invoices.batch.reader.S3ReaderConfig;
import es.netmind.banana_invoices.batch.reader.SimpleReader;
import es.netmind.banana_invoices.batch.writer.ReciboSimpleWriter;
import es.netmind.banana_invoices.batch.writer.SimpleWriter;
import es.netmind.banana_invoices.models.Recibo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@SuppressWarnings({"rawtypes", "unchecked"})
public class AppMainConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    
    @Autowired
    private SynchronizedItemStreamReader<Recibo> s3Reader;
       
    @Bean
    ItemReader<String> simpleRead() {
        return new SimpleReader();
    }

    @Bean
    ItemWriter<String> simpleWrite() {
        return new SimpleWriter();
    }

    @Bean
    ItemProcessor<String, String> simpleProccesor() {
        return new SimpleProcessor();
    }

    @Bean
    ItemProcessor<Recibo, Object> s3Proccesor() {
        return new ReciboValidoProcessor();
    }
    
    @Bean
    ItemProcessor<Recibo, Object> s3PagadoProccesor() {
        return new ReciboPagadoProcessor();
    }
    
    
    @Bean
    public ItemProcessor composeProcessor() {
	    List<ItemProcessor<Recibo,Object>> processors = new ArrayList<ItemProcessor<Recibo,Object>>();
	    processors.add(s3Proccesor());
	    processors.add(s3PagadoProccesor());
	    CompositeItemProcessor<Recibo,Object> compositeProcessor = new CompositeItemProcessor<Recibo,Object>();
	    compositeProcessor.setDelegates(processors);
	    return compositeProcessor;
    }
    
    @Bean
    ItemWriter<Object> s3Writer(){
    	return new ReciboSimpleWriter();
    }	
    
    
    @Bean
    public TaskExecutor taskExecutor() {
    	
    	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();  	
    	taskExecutor.setMaxPoolSize(20);
    	taskExecutor.setQueueCapacity(20);
    	taskExecutor.afterPropertiesSet();
    	return taskExecutor;
    }

    
    
    @Bean
    public Step step1() {
        return steps.get("step1")
                .allowStartIfComplete(true)
                .<String, String>chunk(2)
                .reader(simpleRead())
                .processor(simpleProccesor())
                .writer(simpleWrite())
                .build();
    }

    /*@Bean("mySimpleJob")
    public Job procesadorItems() {
        return jobs.get("job1")
                .start(step1())
                .build();
    }*/
    
    @Bean("Team3Job")
    public Job procesadorItems() {
        return jobs.get("job1")
                .start(step2())
                .build();
    }
    
    @Bean
    public Step step2() {
        return steps.get("step2")
                .allowStartIfComplete(true)
                .<Recibo, Object>chunk(100)
                .reader(s3Reader)
                .processor(composeProcessor())
                .writer(s3Writer())
                .build();
    }
    

    // TODO: IMPLEMENT STEPS AND JOB FOR RECIBO

}