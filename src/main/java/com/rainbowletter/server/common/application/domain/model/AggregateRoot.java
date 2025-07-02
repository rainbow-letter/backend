package com.rainbowletter.server.common.application.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AggregateRoot {

    private final List<Object> domainEvents = new ArrayList<>();

    protected <T extends AggregateRoot, U> void registerEvent(final T instance, final U event) {
        Assert.notNull(instance, "Aggregate root instance must not be null");
        Assert.notNull(event, "Domain event must not be null");
        instance.registerEvent(event);
    }

    protected <T> void registerEvent(final T event) {
        Assert.notNull(event, "Domain event must not be null");
        domainEvents.add(event);
    }

    public Collection<Object> domainEvents() {
        return Collections.unmodifiableCollection(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

}
