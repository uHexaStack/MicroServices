package com.uhexastack.billingservice.billing.domain.model.events;

import java.math.BigDecimal;

public record PaymentProcessedEvent(
    Long orderId,
    BigDecimal amount,
    String currency
) {} 