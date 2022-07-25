package org.parmenter.correlator.distribution;

public interface EventRouter {
    public void deliver(CorrelationEvent event);
}
