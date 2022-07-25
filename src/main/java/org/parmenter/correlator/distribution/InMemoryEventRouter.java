package org.parmenter.correlator.distribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMemoryEventRouter implements EventRouter{

    private static final Logger log = LoggerFactory.getLogger(InMemoryEventRouter.class);
    private Queue eventsQueue;

    public InMemoryEventRouter(){
        this.eventsQueue = new LinkedBlockingQueue<CorrelationEvent>();
    }
    @Override
    public void deliver(CorrelationEvent event) {
        log.info("Added new correlation event");
        eventsQueue.add(event);
    }

    public List<CorrelationEvent> getEvents(){
        return eventsQueue.stream().toList();
    }
}
