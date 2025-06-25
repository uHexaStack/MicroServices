package com.uhexastack.notificationservice.notification.application.internal.outboundservices;

public interface NotificationSender {
    boolean send(String recipient, String payload);
}