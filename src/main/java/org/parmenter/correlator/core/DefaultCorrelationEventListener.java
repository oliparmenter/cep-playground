package org.parmenter.correlator.core;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class DefaultCorrelationEventListener implements UpdateListener {

    SubscriptionManager subsManager;

    public DefaultCorrelationEventListener(SubscriptionManager subscriptionManager){
        this.subsManager = subscriptionManager;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        EventBean event = newEvents[0];
        System.out.printf("Event: %s", event.toString());
        this.subsManager.getSubscriptionsForRule(rule)
    }
}
