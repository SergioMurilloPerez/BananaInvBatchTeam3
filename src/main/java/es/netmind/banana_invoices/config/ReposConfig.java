package es.netmind.banana_invoices.config;

import es.netmind.banana_invoices.persistence.IPropietarioRepo;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.persistence.JPAIReciboRepo;
import es.netmind.banana_invoices.persistence.JPAPropietarioRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReposConfig {

    @Bean
    public IPropietarioRepo propRepository() {
        return new JPAPropietarioRepo();
    }

    @Bean
    public IReciboRepo recRepository() {
        return new JPAIReciboRepo();
    }
}
