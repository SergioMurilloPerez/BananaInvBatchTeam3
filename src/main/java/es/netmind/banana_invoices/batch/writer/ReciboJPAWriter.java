package es.netmind.banana_invoices.batch.writer;

import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.ReciboInvalido;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.persistence.ReciboInvalidoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Setter @Getter
@Transactional
public class ReciboJPAWriter implements ItemWriter<Object> {
    private IReciboRepo reciboRepo;
    private ReciboInvalidoRepository invalidoRepository;

    @Override
    public void write(List<? extends Object> list) throws Exception {
        System.out.println("ReciboJPAWriter write()....:" + list.size());

        //  TODO: TRANSFORM EACH LIST OBJECT TO RECIBO AND VERIFY ITS VALIDTY. STORE IN PROPER TABLE.
    }
}
