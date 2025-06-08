package com.uhexastack.shared.domain.model.events;

import java.util.Map;

public record OrderCreatedEvent(Long orderId, Map<Long, Integer> productQuantities) {}
