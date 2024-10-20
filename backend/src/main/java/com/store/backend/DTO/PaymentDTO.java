package com.store.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long idServicePack;// không quan tâm
    private long amount;
    private String description;
    private String bankCode;
}
