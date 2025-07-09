package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;

import com.uhexastack.notificationservice.shared.domain.model.events.OrderCancelledEvent;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import com.uhexastack.notificationservice.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCancelledEventHandler {

    private final NotificationContextFacade notificationFacade;
    private final ProfileContextFacade profileFacade;

    public OrderCancelledEventHandler(NotificationContextFacade notificationFacade, ProfileContextFacade profileFacade) {
        this.notificationFacade = notificationFacade;
        this.profileFacade = profileFacade;
    }

    @EventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        // For now, we'll send a generic cancellation notification
        // In a real implementation, you'd get the user ID from the order
        String recipient = "customer@example.com"; // This should come from the order
        String payload = "Your order with ID " + event.orderId() + " has been cancelled. If you have any questions, please contact our support team.";
        
        notificationFacade.sendNotification("EMAIL", recipient, payload, event.orderId(), null, null);
    }
} 