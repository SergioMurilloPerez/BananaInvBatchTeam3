package es.netmind.banana_invoices.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Getter @Setter
@NoArgsConstructor
@ToString(callSuper=true)
@Entity
@Table(name = "recibos_invalidos")
public class ReciboInvalido extends Recibo {
    private String errores;

    public ReciboInvalido(Recibo recibo, String errores) {
        super(recibo);
        this.errores = errores;
    }
}
