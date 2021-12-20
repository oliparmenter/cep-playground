package org.parmenter.correlator.data.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelManager {

    public static List modelList(){
        List models = Collections.unmodifiableList(
            Arrays.asList(
                TemperatureReading.class,
                HumidityReading.class,
                PingTime.class
            )
        );
        return models;
    }
}
