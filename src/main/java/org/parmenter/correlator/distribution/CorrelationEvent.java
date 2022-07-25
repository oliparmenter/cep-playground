package org.parmenter.correlator.distribution;

import org.parmenter.correlator.core.Subscription;

public class CorrelationEvent {

    private Subscription triggeredSubscription;
    private DeliveryStatus deliveryStatus;

    public CorrelationEvent(Subscription subscription){
        this.triggeredSubscription = subscription;
        this.deliveryStatus = DeliveryStatus.TRIGGERED;
    }

}
