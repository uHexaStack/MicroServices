package com.uhexastack.paymentservice.payment.application.internal.commandservices;


import com.uhexastack.paymentservice.payment.domain.model.commands.SeedPaymentStatusesCommand;
import com.uhexastack.paymentservice.payment.domain.model.entities.PaymentStatus;
import com.uhexastack.paymentservice.payment.domain.model.valueobjects.PaymentStatusType;
import com.uhexastack.paymentservice.payment.domain.services.PaymentStatusCommandService;
import com.uhexastack.paymentservice.payment.infrastructure.persistence.jpa.repositories.PaymentStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of the {@code PaymentStatusCommandService}.
 * Provides a method to seed payment statuses into the repository.
 */
@Service
public class PaymentStatusCommandServiceImpl implements PaymentStatusCommandService {

    private final PaymentStatusRepository paymentStatusRepository;

    /**
     * Constructs a new {@code PaymentStatusCommandServiceImpl}.
     *
     * @param paymentStatusRepository the repository used to manage payment statuses
     */
    public PaymentStatusCommandServiceImpl(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    /**
     * Handles the {@code SeedPaymentStatusesCommand} by ensuring that all
     * payment status types are saved in the repository.
     *
     * @param command the command to seed payment statuses
     */
    @Override
    public void handle(SeedPaymentStatusesCommand command) {
        Arrays.stream(PaymentStatusType.values()).forEach(type -> {
            if (!paymentStatusRepository.existsByName(type)) {
                paymentStatusRepository.save(PaymentStatus.create(type.name()));
            }
        });
    }
}