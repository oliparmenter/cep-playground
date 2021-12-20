package org.parmenter.correlator.core;

public class Subscription {

    public Rule rule;
    public User subscriber;

    public User getUser() {
        return subscriber;
    }

    public Rule getRule() {
        return rule;
    }
}
