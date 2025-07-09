package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;

import com.uhexastack.notificationservice.shared.domain.model.events.StockLowEvent;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StockLowEventHandler {

    //TODO ADD THE LOW STOCK EVENT FROM INVENTORY CONTEXT
    private final NotificationContextFacade notificationFacade;

    public StockLowEventHandler(NotificationContextFacade notificationFacade) {
        this.notificationFacade = notificationFacade;
    }

    @EventListener
    public void onStockLow(StockLowEvent e) {
        String recipient = "inventario@empresa.pe";
        String payload   = "The stock for product " + e.name()
                + " is low. Current stock: " + e.availableQuantity();

        notificationFacade.sendNotification("SMS", recipient, payload, null, null, null);
    }
}