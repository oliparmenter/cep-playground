package org.parmenter.correlator.core;

import com.espertech.esper.runtime.client.UpdateListener;
import org.parmenter.correlator.distribution.EventRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Subscription {

    private static final Logger log = LoggerFactory.getLogger(Subscription.class);

    private final Rule rule;
    private final User subscriber;
    private final DefaultCorrelationEventListener updateListener;

    public Subscription(Rule rule, User subscriber, EventRouter eventRouter){
        this.rule = rule;
        this.subscriber = subscriber;
        this.updateListener = DefaultCorrelationEventListener.createEventListener(eventRouter, this);
    }

    public User getUser() {
        return subscriber;
    }

    public Rule getRule() {
        return rule;
    }

    public UpdateListener getUpdateListener(){
        return this.updateListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return rule.equals(that.rule) && subscriber.equals(that.subscriber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, subscriber);
    }
}
