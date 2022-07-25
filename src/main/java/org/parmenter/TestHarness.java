package org.parmenter;

import com.espertech.esper.common.client.configuration.Configuration;
import org.parmenter.correlator.core.*;
import org.parmenter.correlator.data.DataStreamEvent;
import org.parmenter.correlator.data.models.TemperatureReading;
import org.parmenter.correlator.distribution.CorrelationEvent;
import org.parmenter.correlator.distribution.EventRouter;
import org.parmenter.correlator.distribution.InMemoryEventRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Test Bed to Demo the EPL
 */
public class TestHarness {

    private static final Logger log = LoggerFactory.getLogger(TestHarness.class);

    private static void setupUsersAndSubscriptions(SubscriptionManager subscriptionManager, EventRouter eventRouter){
        /*
            User in the system who want to get notified about events
         */
        User testUser1 = new User("tester1", "test1@home.com", "User");
        User testUser2 = new User("tester2", "test2@home.com", "User");

        /*
            Rules + Subscriptions based on the combo of rule + user
         */
        String highTempEPLString = "select * from TemperatureReading where reading >= 25.0";
        Rule highTempRule = new Rule(highTempEPLString, "High Temperature Alert");
        Subscription highTempSubscription1 = new Subscription(highTempRule, testUser1, eventRouter);
        Subscription highTempSubscription2 = new Subscription(highTempRule, testUser2, eventRouter);

        String lowTempEPLString = "select * from TemperatureReading where reading < 10.0";
        Rule lowTempRule = new Rule(lowTempEPLString, "Low Temperature Alert");
        Subscription lowTempSubscription = new Subscription(lowTempRule, testUser2, eventRouter);

        /*
            Add subscriptions into engine - this can be done "in process" quite easily
         */
        subscriptionManager.addSubscription(highTempSubscription1);
        subscriptionManager.addSubscription(highTempSubscription2);
        subscriptionManager.addSubscription(lowTempSubscription);
    }

    public static void main(String[] args) {
        try {
            InMemoryEventRouter outputEventRouter = new InMemoryEventRouter();
            SubscriptionManager subscriptionManager = new SubscriptionManager();

            setupUsersAndSubscriptions(subscriptionManager, outputEventRouter);

            CorrelatorService service = new CorrelatorService(subscriptionManager);
            service.startEngine();

            //Generate some temp readings and inject them - simulate the event streams
            simulateEvents(service);

            List<CorrelationEvent> eventsGenerated = outputEventRouter.getEvents();
            for(CorrelationEvent event : eventsGenerated){
                log.info(event.toString());
            }
            log.info("Events Generated: {}\n", eventsGenerated.size());

            service.stopEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Done");
    }

    private static void simulateEvents(CorrelatorService service) {
        List<DataStreamEvent> eventStream = getTemperatureReadings();

        for (DataStreamEvent event : eventStream) {
            service.newEvent(event);
        }
    }

    private static List<DataStreamEvent> getTemperatureReadings() {
        List<DataStreamEvent> events = new ArrayList<>();

        List<String> sensors = Arrays.asList("bedroom", "bathroom", "livingroom");
        Random rnd = new Random();

        for (int i = 0; i < 100; i++) {
            TemperatureReading event = new TemperatureReading(getRandomEntry(sensors), "C", rnd.nextDouble(-1, 35));
            events.add(event);
            log.info("Event: %s\n", event);
        }
        return events;
    }

    public static <T> T getRandomEntry(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
