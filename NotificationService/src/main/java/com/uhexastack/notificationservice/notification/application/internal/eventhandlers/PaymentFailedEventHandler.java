package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;

import com.uhexastack.notificationservice.shared.domain.model.events.PaymentFailedEvent;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import com.uhexastack.notificationservice.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentFailedEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public PaymentFailedEventHandler(NotificationContextFacade notificationFacade, ProfileContextFacade profileFacade) {
        this.notificationFacade = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onPaymentFailed(PaymentFailedEvent e) {
        String recipient = profileFacade.getContactEmailByUserId(e.orderId());
        String payload   = "Your payment for order " + e.orderId()
                + " has failed. Please check your payment details and try again.";

        notificationFacade.sendNotification("EMAIL", recipient, payload, e.orderId(), null, null);
    }
}
