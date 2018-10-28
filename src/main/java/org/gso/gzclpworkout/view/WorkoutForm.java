package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.event.WorkoutUpdateEvent;
import org.gso.gzclpworkout.model.Workout;
import org.gso.gzclpworkout.repository.WorkoutRepository;
import org.gso.gzclpworkout.utils.FormUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class WorkoutForm extends FormLayout {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ApplicationEventPublisher eventPublisher;
    private final WorkoutRepository workoutRepository;

    private TextField name = new TextField();

    private Workout workout;
    private Binder<Workout> binder = new Binder<>(Workout.class);
    private Dialog dialog;

    public WorkoutForm(ApplicationEventPublisher eventPublisher, WorkoutRepository workoutRepository) {
        this.eventPublisher = eventPublisher;
        this.workoutRepository = workoutRepository;
    }

    void openDialog() {
        setWorkout(new Workout());
        dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        name.setPlaceholder("My workout");
        name.setRequiredIndicatorVisible(true);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Please add the workout name", 1, 255))
                .bind(Workout::getName, Workout::setName);
        addFormItem(name, "Name");

        Button confirm = new Button(new Icon(VaadinIcon.CHECK_CIRCLE),
                e -> FormUtils.checkFormValidity(binder, this::saveWorkout));
        Button cancel = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE), e -> dialog.close());

        confirm.getElement().getStyle().set("color", "green");
        cancel.getElement().getStyle().set("color", "red");

        dialog.add(this, confirm, cancel);
        dialog.open();
    }

    private void saveWorkout() {
        LOGGER.info(() -> "Saving " + workout);
        workoutRepository.save(workout);
        LOGGER.info(() -> "Saved " + workout);
        publishWorkoutEvent();
        dialog.close();
    }

    private void publishWorkoutEvent() {
        LOGGER.debug(() -> "Publishing WorkoutUpdateEvent");
        eventPublisher.publishEvent(new WorkoutUpdateEvent(this));
    }

    private void setWorkout(Workout workout) {
        this.workout = workout;
        binder.setBean(workout);
    }
}
