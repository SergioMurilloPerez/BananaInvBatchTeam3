package es.netmind.banana_invoices.batch.writer;

import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.models.ReciboInvalido;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.persistence.ReciboInvalidoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Setter @Getter
@Transactional
public class ReciboJPAWriter implements ItemWriter<Object> {
    
	@Autowired
	private IReciboRepo reciboRepo;
	
	@Autowired
    private ReciboInvalidoRepository invalidoRepository;

    @Override
    public void write(List<? extends Object> list) throws Exception {
        System.out.println("ReciboJPAWriter write()....:" + list.size());

        //  TODO: TRANSFORM EACH LIST OBJECT TO RECIBO AND VERIFY ITS VALIDTY. STORE IN PROPER TABLE.        
        Recibo currentRecibo = null;
        ReciboInvalido currentReciboInv = null;
        for (Object item : list) {
            currentRecibo = (Recibo) item;

            
            if (!currentRecibo.isValido()) {
                currentReciboInv = (ReciboInvalido) item;
                currentReciboInv.setId(null);
                System.out.printf("\t ...consolidating INVALIDO: %s\n", currentReciboInv);
                invalidoRepository.save(currentReciboInv);
            }else{
            	currentRecibo.setId(null);
                System.out.printf("\t ...consolidating VALIDO: %s\n", currentRecibo);
                reciboRepo.save(currentRecibo);
            }
        }
        
    }
}
