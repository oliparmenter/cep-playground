package org.parmenter.correlator.data.models;

import org.parmenter.correlator.data.DataStreamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TemperatureReading implements DataStreamEvent {

    private static final Logger log = LoggerFactory.getLogger(TemperatureReading.class);

    private LocalDateTime eventTime;
    private String sensorName;
    private String unit;
    private Double reading;

    private static String EVENT_STREAM_NAME = "TemperatureReading";

    public String getSensorName() {
        return sensorName;
    }

    public String getUnit() {
        return unit;
    }

    public Double getReading() {
        return reading;
    }

    public TemperatureReading(String sensorName, String unit, Double reading) {
        this.eventTime = LocalDateTime.now();
        this.sensorName = sensorName;
        this.unit = unit;
        this.reading = reading;
    }

    @Override
    public String getEventId() {
        return sensorName + "-" + eventTime.toString();
    }

    @Override
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    @Override
    public String getEventStreamName() {
        return EVENT_STREAM_NAME;
    }

    @Override
    public Object getEventData() {
        return null;
    }

    @Override
    public String toString() {
        return "TemperatureReading{" +
                "eventTime=" + eventTime +
                ", sensorName='" + sensorName + '\'' +
                ", unit='" + unit + '\'' +
                ", reading=" + reading +
                '}';
    }
}
