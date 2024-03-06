package com.andrelopes.transaction_api.dtos;

public record TransactionDTO(

        Float value,
        Long payer,
        Long payee

) {
}
