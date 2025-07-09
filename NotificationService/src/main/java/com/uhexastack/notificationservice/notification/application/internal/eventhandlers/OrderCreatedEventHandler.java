package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;

import com.uhexastack.notificationservice.shared.domain.model.events.OrderCreatedEvent;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import com.uhexastack.notificationservice.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public OrderCreatedEventHandler(NotificationContextFacade notificationFacade, ProfileContextFacade profileFacade) {
        this.notificationFacade = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        // For now, we'll send a generic order confirmation
        // In a real implementation, you'd get the user ID from the order
        String recipient = "customer@example.com"; // This should come from the order
        String payload = "Your order with ID " + event.orderId() + " has been created successfully. We'll notify you when it's ready to ship.";
        
        notificationFacade.sendNotification("EMAIL", recipient, payload, event.orderId(), null, null);
    }
} 