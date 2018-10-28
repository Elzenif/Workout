package org.gso.gzclpworkout.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.view.ExerciseForm;
import org.springframework.context.ApplicationEvent;

public class ExerciseUpdateEvent extends ApplicationEvent {

    private static final Logger LOGGER = LogManager.getLogger();

    public ExerciseUpdateEvent(ExerciseForm source) {
        super(source);
        LOGGER.debug(() -> "Event published");
    }
}
