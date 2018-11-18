package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        addButtons();
    }

    private void addButtons() {
        Button listButton = new Button("Exercises", e -> goTo(ExerciseView.class));
        Button workoutButton = new Button("Workouts", e -> goTo(WorkoutMainView.class));
        add(listButton, workoutButton);
    }

    private void goTo(Class<? extends Component> navigationTarget) {
        getUI().ifPresent(ui -> ui.navigate(navigationTarget));
    }
}
