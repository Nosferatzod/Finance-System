package com.example.demo.dto;

import com.example.demo.models.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionDTO(
        String name,
        BigDecimal value,
        String description,
        LocalDate date,
        TransactionType type,
        Long userId,
        Long accountId,
        Long creditCardId,
        Long destinationId
) {}