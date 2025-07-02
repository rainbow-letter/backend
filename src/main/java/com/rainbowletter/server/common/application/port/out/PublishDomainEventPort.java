package com.rainbowletter.server.common.application.port.out;

import com.rainbowletter.server.common.application.domain.model.AggregateRoot;

public interface PublishDomainEventPort {

    <T extends AggregateRoot> void publish(T instance);

}
