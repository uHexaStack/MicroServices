package com.uhexastack.notificationservice.shared.interfaces.acl;

public interface NotificationContextFacade {
    void sendNotification(String type, String recipient, String payload, Long orderId, Long invoiceId, Long paymentId);
} 