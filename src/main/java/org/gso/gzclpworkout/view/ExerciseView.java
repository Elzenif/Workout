package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.event.ExerciseUpdateEvent;
import org.gso.gzclpworkout.model.Exercise;
import org.gso.gzclpworkout.repository.ExerciseRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Route("exercises")
@Component
@UIScope
public class ExerciseView extends VerticalLayout implements ApplicationListener<ExerciseUpdateEvent> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ExerciseRepository exerciseRepository;
    private final ExerciseForm exerciseForm;

    private final Grid<Exercise> grid = new Grid<>();
    private final TextField filterTextField = new TextField();

    public ExerciseView(ExerciseRepository exerciseRepository, ExerciseForm exerciseForm) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseForm = exerciseForm;

        setup();
    }

    @Override
    public void onApplicationEvent(@NotNull ExerciseUpdateEvent event) {
        LOGGER.debug(() -> "ExerciseUpdateEvent Listened");
        updateList();
    }

    private void setup() {
        VerticalLayout toolbar = setupToolbar();
        HorizontalLayout mainLayout = setupMainLayout();

        add(toolbar, mainLayout);
        setHeight("100%");
        updateList();
    }

    private HorizontalLayout setupMainLayout() {
        grid.addColumn(Exercise::getName).setHeader("Name");
        grid.addColumn(e -> e.isPrimary() ? "Yes" : "No").setHeader("Is primary");
        grid.addColumn(Exercise::getCategory).setHeader("Category");
        grid.addColumn(Exercise::getDescription).setHeader("Description");
        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(e -> exerciseForm.setExercise(e.getValue()));

        HorizontalLayout mainLayout = new HorizontalLayout(grid, exerciseForm);
        mainLayout.setHeight("80%");
        mainLayout.setWidth("100%");
        return mainLayout;
    }

    @NotNull
    @Contract(" -> new")
    private VerticalLayout setupToolbar() {
        Button homeButton = new HomeButton();
        TextField textField = setupFilterLayout();
        Button newExerciseButton = setupNewExerciseButton();
        VerticalLayout layout = new VerticalLayout(homeButton, textField, newExerciseButton);
        layout.setHeight("20%");
        return layout;
    }

    @Contract(" -> new")
    @NotNull
    private Button setupNewExerciseButton() {
        return new Button("New exercise", e -> {
            grid.asSingleSelect().clear();
            exerciseForm.setExercise(new Exercise());
        });
    }

    @Contract(" -> new")
    private TextField setupFilterLayout() {
        filterTextField.setPlaceholder("Filter by name...");
        filterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        filterTextField.addValueChangeListener(e -> updateList());

        Button button = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE),
                e -> filterTextField.clear());
        filterTextField.setSuffixComponent(button);

        return filterTextField;
    }

    private void updateList() {
        LOGGER.debug(() -> "Updating the list of exercises");
        String filterName = filterTextField.getValue();
        List<Exercise> exercises;
        if (StringUtils.isEmpty(filterName)) {
            exercises = exerciseRepository.findAll();
        } else {
            exercises = exerciseRepository.findByNameContaining(filterName);
        }
        grid.setItems(exercises);
        LOGGER.debug(() -> "Updated list of exercises: " + exercises);
    }
}
