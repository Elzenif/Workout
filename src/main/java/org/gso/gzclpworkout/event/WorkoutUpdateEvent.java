package org.gso.gzclpworkout.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.view.WorkoutForm;
import org.springframework.context.ApplicationEvent;

public class WorkoutUpdateEvent extends ApplicationEvent {

    private static final Logger LOGGER = LogManager.getLogger();

    public WorkoutUpdateEvent(WorkoutForm source) {
        super(source);
        LOGGER.debug(() -> "WorkoutUpdateEvent published");
    }
}
