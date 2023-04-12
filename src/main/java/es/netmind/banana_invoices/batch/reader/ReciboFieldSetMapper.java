package es.netmind.banana_invoices.batch.reader;

import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import static es.netmind.banana_invoices.util.BatchUtil.*;

public class ReciboFieldSetMapper implements FieldSetMapper<Recibo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Recibo mapFieldSet(FieldSet fieldSet) throws BindException {
        Recibo rec = new Recibo();
        
        if (isValidField(fieldSet, "id_remoto")) rec.setId(Long.parseLong(fieldSet.readString("id_remoto")));
        if (isValidField(fieldSet, "direccion_contacto")) rec.setDireccion_contacto(fieldSet.readString("direccion_contacto"));
        if (isValidField(fieldSet, "direccion_envio")) rec.setDireccion_envio(fieldSet.readString("direccion_envio"));
        if (isValidField(fieldSet, "fecha_emision")) rec.setFecha_emision(convertToLocalDate(fieldSet.readDate("fecha_emision")));
        if (isValidField(fieldSet, "fecha_vencimiento")) rec.setFecha_vencimiento(convertToLocalDate(fieldSet.readDate("fecha_vencimiento")));
        if (isValidField(fieldSet, "nombre_contacto")) rec.setNombre_contacto(fieldSet.readString("nombre_contacto"));
        if (isValidField(fieldSet, "propietario")) rec.setPropietario(new Propietario(fieldSet.readLong("propietario")));
        if (isValidField(fieldSet, "nombre_producto")) rec.setNombre_producto(fieldSet.readString("nombre_producto"));
        if (isValidField(fieldSet, "cantidad")) rec.setCantidad(fieldSet.readInt("cantidad"));
        if (isValidField(fieldSet, "precio_unitario")) rec.setPrecio_unitario(fieldSet.readFloat("precio_unitario"));
        if (isValidField(fieldSet, "base_imponible")) rec.setBase_imponible(fieldSet.readDouble("base_imponible"));
        if (isValidField(fieldSet, "impuestos")) rec.setImpuestos(fieldSet.readFloat("impuestos"));
        if (isValidField(fieldSet, "total")) rec.setTotal(fieldSet.readDouble("total"));

        return rec;
    }
}
