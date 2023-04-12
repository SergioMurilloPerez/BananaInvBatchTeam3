package es.netmind.banana_invoices.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class PaidStatus {
    private Long id;
    private Boolean paid;
}
