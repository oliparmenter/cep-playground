package org.parmenter.correlator.data;

import java.time.LocalDateTime;

public interface DataStreamEvent {
    public String getEventId();
    public LocalDateTime getEventTime();
    public String getEventStreamName();
    public Object getEventData();
}
