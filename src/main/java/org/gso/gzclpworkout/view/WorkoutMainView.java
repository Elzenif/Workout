package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.event.WorkoutUpdateEvent;
import org.gso.gzclpworkout.model.Workout;
import org.gso.gzclpworkout.repository.WorkoutRepository;
import org.gso.gzclpworkout.view.buttons.HomeButton;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Route("workouts")
@Component
@UIScope
public class WorkoutMainView extends VerticalLayout implements ApplicationListener<WorkoutUpdateEvent> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final WorkoutRepository workoutRepository;
    private final WorkoutForm workoutForm;

    private final Grid<Workout> grid = new Grid<>();

    public WorkoutMainView(WorkoutRepository workoutRepository, WorkoutForm workoutForm) {
        this.workoutRepository = workoutRepository;
        this.workoutForm = workoutForm;

        setup();
    }

    @Override
    public void onApplicationEvent(@NotNull WorkoutUpdateEvent event) {
        LOGGER.debug(() -> "ExerciseUpdateEvent Listened");
        updateList();
    }

    private void setup() {
        VerticalLayout toolbar = setupToolbar();

        grid.addColumn(Workout::getName).setHeader("Name");
        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(e -> goToWorkout(e.getValue().getId()));

        add(toolbar, grid);
        setHeight("100%");
        updateList();
    }

    private void goToWorkout(Long workoutId) {
        getUI().ifPresent(ui -> ui.navigate(WorkoutView.class, workoutId));
    }

    @NotNull
    private VerticalLayout setupToolbar() {
        Button homeButton = new HomeButton();
        Button newWorkoutButton = new Button("New workout", e -> newWorkoutDialog());
        return new VerticalLayout(homeButton, newWorkoutButton);
    }

    private void updateList() {
        LOGGER.debug(() -> "Updating the list of workouts");
        List<Workout> workouts = workoutRepository.findAll();
        grid.setItems(workouts);
        LOGGER.debug(() -> "Updated list of workouts: " + workouts);
    }

    private void newWorkoutDialog() {
        workoutForm.openDialog();
    }
}
