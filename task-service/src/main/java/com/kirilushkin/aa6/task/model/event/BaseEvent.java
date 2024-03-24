package com.kirilushkin.aa6.task.model.event;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEvent <T> {

    private String eventId;
    private Integer eventVersion;
    private String eventName;
    private LocalDateTime eventTime;
    private String eventProducer;
    private T data;
}
