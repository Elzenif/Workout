package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Route("workouts")
@Component
@UIScope
public class WorkoutView extends VerticalLayout {

    private final WorkoutForm workoutForm;

    public WorkoutView(WorkoutForm workoutForm) {
        this.workoutForm = workoutForm;

        printWorkouts();
    }

    private void printWorkouts() {
        Button homeButton = new HomeButton();
        Button newWorkoutButton = new Button("New workout", e -> newWorkoutDialog());
        add(homeButton, newWorkoutButton);
    }

    private void newWorkoutDialog() {
        workoutForm.openDialog();
    }
}
