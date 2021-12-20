package org.parmenter;

import org.parmenter.correlator.data.DataStreamEvent;
import org.parmenter.correlator.data.models.TemperatureReading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Hello world!
 */
public class TestHarness {
    public static void main(String[] args) {
        CorrelatorService service = new CorrelatorService();
        service.startEngine();

        //Generate some temp readings and inject them
        List<DataStreamEvent> eventStream = getTempReadings();

        for(DataStreamEvent event : eventStream){
            service.newEvent(event)
        }

    }

    private static List<DataStreamEvent> getTempReadings() {
        List<DataStreamEvent> events = new ArrayList<>();

        List<String> sensors = Arrays.asList("bedroom", "bathroom", "livingroom");
        Random rnd = new Random();

        for (int i = 0; i < 100; i++) {
            TemperatureReading event = new TemperatureReading(
                    getRandomEntry(sensors),
                    "C",
                    rnd.nextDouble(-10, 50)
            );
            events.add(event);
            System.out.printf("Event: %s\n", event.toString());
        }
        return events;
    }

    public static <T> T getRandomEntry(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
