package es.netmind.banana_invoices.models;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ReciboPropietario {
    @NonNull
    @Min(1)
    private Long recibo;
    @NonNull
    @Min(1)
    private Long propietario;
}
