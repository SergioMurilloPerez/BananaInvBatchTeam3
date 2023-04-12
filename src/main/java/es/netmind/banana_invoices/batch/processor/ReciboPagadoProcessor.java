package es.netmind.banana_invoices.batch.processor;

import es.netmind.banana_invoices.models.PaidStatus;
import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.ReciboInvalido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ReciboPagadoProcessor implements ItemProcessor<Recibo, Recibo> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //TODO: HERE INJECT PROPERTY api.verification.url
    private String apiUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Recibo process(Recibo recibo) throws Exception {
        // TODO: HERE GET PAID STATUS AND PROCESS RECIBO
        return null;
    }

    private PaidStatus fetchVerificationDataFromAPI(Long id) throws NoSuchFieldException {
        if (apiUrl != null) {
            String endpoint = apiUrl + id;
            ResponseEntity<PaidStatus> response = restTemplate.getForEntity(endpoint, PaidStatus.class);
            PaidStatus veriData = response.getBody();
            logger.info("veriData:" + veriData);
            return veriData;
        } else throw new NoSuchFieldException("apiUrl not defined");
    }
}
