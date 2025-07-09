package com.uhexastack.notificationservice.shared.interfaces.acl;

public interface ProfileContextFacade {
    String getContactEmailByUserId(Long userId);
    String getContactPhoneByUserId(Long userId);
} 