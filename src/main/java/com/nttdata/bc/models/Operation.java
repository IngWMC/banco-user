package com.nttdata.bc.models;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    private Integer operationId;
    private Integer accountId;
    private Integer debitCardId;
    private Integer creditCardId;
    private Integer creditId;
    private String operationType;
    private BigDecimal amount;
    private Instant createdAt;
}
