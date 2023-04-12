package es.netmind.banana_invoices.services;

import es.netmind.banana_invoices.exceptions.NullElementException;
import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;

import java.util.List;
import java.util.Set;

public interface IInventario {
    public List<Propietario> findAllPropietarios();

    public Propietario savePropietario(Propietario prop)throws NullElementException;

    public List<Recibo> findAllRecibos();

    public Recibo saveRecibo(Recibo rec)throws NullElementException;

    public Recibo asocia(Long recId, Long propId)throws NullElementException;

    public Set<String> esValidoRecibo(Long id);

    public boolean estaPagadoRecibo(Long id);

}
