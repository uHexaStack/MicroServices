package com.uhexastack.notificationservice.shared.infrastructure.acl;

import com.uhexastack.notificationservice.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {
    
    @Override
    public String getContactEmailByUserId(Long userId) {
        // TODO: Implement actual user profile lookup
        // For now, return a default email based on userId
        return "user" + userId + "@example.com";
    }
    
    @Override
    public String getContactPhoneByUserId(Long userId) {
        // TODO: Implement actual user profile lookup
        // For now, return a default phone number based on userId
        return "+1234567890";
    }
} 