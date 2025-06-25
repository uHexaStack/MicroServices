package com.uhexastack.paymentservice.payment.domain.services;

import com.uhexastack.paymentservice.payment.domain.model.commands.SeedPaymentStatusesCommand;
/**
 * Service interface for handling payment status commands.
 */
public interface PaymentStatusCommandService {

    /**
     * Processes the given {@code SeedPaymentStatusesCommand} to seed payment statuses.
     *
     * @param command the command containing instructions to seed payment statuses
     */
    void handle(SeedPaymentStatusesCommand command);
}