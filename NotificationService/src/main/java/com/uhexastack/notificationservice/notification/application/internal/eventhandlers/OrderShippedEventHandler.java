package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;

import com.uhexastack.notificationservice.shared.domain.model.events.OrderShippedEvent;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import com.uhexastack.notificationservice.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderShippedEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public OrderShippedEventHandler(NotificationContextFacade notificationFacade,
                                   ProfileContextFacade profileFacade) {
        this.notificationFacade        = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onOrderShipped(OrderShippedEvent e) {

        String recipient = profileFacade.getContactEmailByUserId(e.userId());

        String payload   = "Your order with ID " + e.orderId() + " has been shipped. Thank you for shopping with us!";

        notificationFacade.sendNotification("EMAIL", recipient, payload, e.orderId(), null, null);
    }
}