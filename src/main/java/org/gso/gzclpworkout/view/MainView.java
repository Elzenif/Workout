package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.gso.gzclpworkout.view.buttons.ExerciseButton;
import org.gso.gzclpworkout.view.buttons.WorkoutButton;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        addButtons();
    }

    private void addButtons() {
        add(new ExerciseButton(), new WorkoutButton());
    }
}
