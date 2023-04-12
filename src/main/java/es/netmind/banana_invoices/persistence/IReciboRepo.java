package es.netmind.banana_invoices.persistence;

import es.netmind.banana_invoices.models.Recibo;

import java.util.List;

public interface IReciboRepo {
    public List<Recibo> findAll();

    public Recibo save(Recibo recibo);

    Recibo findById(Long id);
}
