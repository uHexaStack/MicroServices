package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;


import com.uhexastack.notificationservice.notification.domain.services.NotificationCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationPaymentProcessedEventHandler {

    //TODO refactor to use NotificationContextFacade
    private final NotificationCommandService commandService;
    //private final ProfileContextFacade profileFacade;

    public NotificationPaymentProcessedEventHandler(
            NotificationCommandService commandService,
            ProfileContextFacade       profileFacade
    ) {
        this.commandService = commandService;
        this.profileFacade   = profileFacade;
    }

    @EventListener
    public void onPaymentProcessed(PaymentProcessedEvent e) {
        String email = profileFacade.getContactEmailByUserId(e.userId());

        String payload = String.format(
                "Tu pago (ID: %d) para la orden #%d se ha procesado correctamente.",
                e.paymentId(), e.orderId()
        );

        commandService.handle(new SendNotificationCommand(
                "EMAIL", email, payload,
                e.orderId(), null, e.paymentId()
        ));
    }
}