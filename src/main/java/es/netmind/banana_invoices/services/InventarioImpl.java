package es.netmind.banana_invoices.services;

import es.netmind.banana_invoices.exceptions.NullElementException;
import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;
import es.netmind.banana_invoices.persistence.IPropietarioRepo;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.support.NullValue;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class InventarioImpl implements IInventario {
    @Getter
    @Setter
    private IPropietarioRepo propietariosRepo;

    @Getter
    @Setter
    private IReciboRepo recibosRepo;

    @Override
    public List<Propietario> findAllPropietarios() {
        return propietariosRepo.findAll();
    }

    @Override
    @Transactional
    public Propietario savePropietario(Propietario prop) {
        return propietariosRepo.save(prop);
    }

    @Override
    public List<Recibo> findAllRecibos() {
        return recibosRepo.findAll();
    }

    @Override
    @Transactional
    public Recibo saveRecibo(Recibo rec) {
        return recibosRepo.save(rec);
    }

    @Override
    @Transactional
    public Recibo asocia(Long recId, Long propId) throws NullElementException {
        Recibo rec = recibosRepo.findById(recId);
        Propietario prop = propietariosRepo.findById(propId);
        if (rec != null && prop != null) {
            rec.setPropietario(prop);
            recibosRepo.save(rec);
            return rec;
        } else throw new NullElementException("Recibo o propietario nulos");
    }

    @Override
    public Set<String> esValidoRecibo(Long id)throws NullElementException {
        Recibo rec = recibosRepo.findById(id);
        if (rec != null) return rec.esValido();
        else throw new NullElementException("Recibo nulo");
    }

    @Override
    public boolean estaPagadoRecibo(Long id) {
        Recibo rec = recibosRepo.findById(id);
        if (rec != null) return rec.isEstado();
        else throw new NullElementException("Recibo nulo");
    }
}
