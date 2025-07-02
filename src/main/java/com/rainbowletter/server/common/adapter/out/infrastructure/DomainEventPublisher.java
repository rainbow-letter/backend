package com.rainbowletter.server.common.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.application.domain.model.AggregateRoot;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@InfraAdapter
@RequiredArgsConstructor
class DomainEventPublisher implements PublishDomainEventPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public <T extends AggregateRoot> void publish(final T instance) {
        instance.domainEvents().forEach(eventPublisher::publishEvent);
        instance.clearDomainEvents();
    }

}
