package es.netmind.banana_invoices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class BananaInvoicesBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BananaInvoicesBatchApplication.class, args);
    }

}
