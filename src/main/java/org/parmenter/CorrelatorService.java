package org.parmenter;

import org.parmenter.correlator.core.Engine;
import org.parmenter.correlator.core.SubscriptionManager;
import org.parmenter.correlator.data.DataStreamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class CorrelatorService{
    private static final Logger log = LoggerFactory.getLogger(CorrelatorService.class);

    Engine engine;
    SubscriptionManager subscriptionManager;

    public CorrelatorService(SubscriptionManager subscriptionManager){
        log.info("Created new Correlation Service");
        engine = new Engine();
        this.subscriptionManager = subscriptionManager;
    }

    public void startEngine(){
        this.subscriptionManager.initialiseSubscriptions(engine);
    }

    public void stopEngine(){
        this.engine.getRuntime().destroy();
        log.info("Shutting down engine");
    }

    public void newEvent(DataStreamEvent event) {
        if(!engine.isInitialised()){
            throw new RuntimeException("Engine not ready");
        }

        engine.newEvent(event);
    }
}
