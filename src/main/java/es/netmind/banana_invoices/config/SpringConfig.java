package es.netmind.banana_invoices.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ReposConfig.class, ServicesConfig.class})
@ComponentScan(basePackages = "es.netmind.banana_invoices.batch.config.AppMainConfig")
public class SpringConfig {

}
