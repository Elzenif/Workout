package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.gso.gzclpworkout.model.Workout;
import org.gso.gzclpworkout.repository.WorkoutRepository;
import org.gso.gzclpworkout.view.buttons.WorkoutButton;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Route(value = "workouts")
@Component
@UIScope
public class WorkoutView extends VerticalLayout implements HasUrlParameter<Long>, AfterNavigationObserver {

    private final WorkoutRepository workoutRepository;
    private Workout workout;

    public WorkoutView(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    private void setup() {
        HorizontalLayout toolbar = setupToolbar();
        add(toolbar);
        add(new Label(workout.getName()));

    }

    @NotNull
    @Contract(" -> new")
    private HorizontalLayout setupToolbar() {
        return new HorizontalLayout(new WorkoutButton());
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long workoutId) {
        Optional<Workout> optional = workoutRepository.findById(workoutId);
        if (optional.isPresent()) {
            workout = optional.get();
        } else {
            String message = String.format("Workout with id %d not found", workoutId);
            beforeEvent.rerouteToError(NotFoundException.class, message);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        removeAll();
        setup();
    }
}
