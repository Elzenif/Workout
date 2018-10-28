package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        addButtons();
    }

    private void addButtons() {
        Button listButton = new Button("See exercises",
                e -> printExercises());
        add(listButton);
    }

    private void printExercises() {
        getUI().ifPresent(ui -> ui.navigate("exercises"));
    }
}
