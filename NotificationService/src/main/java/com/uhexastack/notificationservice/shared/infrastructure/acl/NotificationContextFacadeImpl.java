package com.uhexastack.notificationservice.shared.infrastructure.acl;

import com.uhexastack.notificationservice.notification.domain.model.commands.SendNotificationCommand;
import com.uhexastack.notificationservice.notification.domain.services.NotificationCommandService;
import com.uhexastack.notificationservice.shared.interfaces.acl.NotificationContextFacade;
import org.springframework.stereotype.Service;

@Service
public class NotificationContextFacadeImpl implements NotificationContextFacade {
    
    private final NotificationCommandService notificationCommandService;
    
    public NotificationContextFacadeImpl(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }
    
    @Override
    public void sendNotification(String type, String recipient, String payload, Long orderId, Long invoiceId, Long paymentId) {
        SendNotificationCommand command = new SendNotificationCommand(type, recipient, payload, orderId, invoiceId, paymentId);
        notificationCommandService.handle(command);
    }
} 