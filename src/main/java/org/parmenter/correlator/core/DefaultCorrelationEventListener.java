package org.parmenter.correlator.core;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import org.parmenter.correlator.data.models.TemperatureReading;
import org.parmenter.correlator.distribution.CorrelationEvent;
import org.parmenter.correlator.distribution.EventRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCorrelationEventListener implements UpdateListener {

    private static final Logger log = LoggerFactory.getLogger(DefaultCorrelationEventListener.class);

    private final EventRouter eventRouter;
    private final Subscription parentSusbcription;

    public static DefaultCorrelationEventListener createEventListener(EventRouter eventRouter, Subscription parentSubscription){
        return new DefaultCorrelationEventListener(eventRouter, parentSubscription);
    }

    private DefaultCorrelationEventListener(EventRouter eventRouter, Subscription parentSubscription){
        this.eventRouter = eventRouter;
        this.parentSusbcription = parentSubscription;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        EventBean event = newEvents[0];
        log.info("Event: {}\n", event.toString());
        CorrelationEvent correlationEvent = new CorrelationEvent(this.parentSusbcription);
        eventRouter.deliver(correlationEvent);
    }
}
