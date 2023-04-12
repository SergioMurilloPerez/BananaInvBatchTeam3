package es.netmind.banana_invoices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @RequiredArgsConstructor
@ToString(exclude = "recibos")
@Entity
@Table(name = "propietario")
public class Propietario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @Column
    @NonNull
    private String nombre;
    @Column
    @NonNull
    private String email;
    @Column
    @NonNull
    private String telefono;
    @Column
    @NonNull
    private int seccion;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Recibo> recibos;

    public Propietario(long pid) {
        this.pid=pid;
    }
}
