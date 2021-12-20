package org.parmenter.correlator.data;

import java.time.LocalDateTime;

public abstract class DataStreamEventBase implements DataStreamEvent {

    public String getEventId() {
        throw new UnsupportedOperationException();
    }

    public LocalDateTime getEventTime() {
        throw new UnsupportedOperationException();
    }

    public String getEventStreamName() {
        throw new UnsupportedOperationException();
    }

    public Object getEventData() {
        throw new UnsupportedOperationException();
    }
}
