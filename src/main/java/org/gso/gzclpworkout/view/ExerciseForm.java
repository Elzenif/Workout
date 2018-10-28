package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gso.gzclpworkout.event.ExerciseUpdateEvent;
import org.gso.gzclpworkout.model.Category;
import org.gso.gzclpworkout.model.Exercise;
import org.gso.gzclpworkout.repository.ExerciseRepository;
import org.gso.gzclpworkout.utils.FormUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ExerciseForm extends FormLayout {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ApplicationEventPublisher eventPublisher;
    private final ExerciseRepository exerciseRepository;

    private TextField name = new TextField();
    private ComboBox<Category> category = new ComboBox<>();
    private TextArea description = new TextArea();

    private Exercise exercise;
    private Binder<Exercise> binder = new Binder<>(Exercise.class);

    public ExerciseForm(ApplicationEventPublisher eventPublisher, ExerciseRepository exerciseRepository) {
        this.eventPublisher = eventPublisher;
        this.exerciseRepository = exerciseRepository;

        setup();
    }

    private void setup() {
        setupFields();
        HorizontalLayout buttonLayout = setupButtons();
        add(buttonLayout);
        setEnabled(false);
    }

    private void setupFields() {
        name.setPlaceholder("Name...");
        name.setRequiredIndicatorVisible(true);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Please add the exercise name", 1, 255))
                .bind(Exercise::getName, Exercise::setName);
        addFormItem(name, "Name");

        category.setItems(Category.values());
        category.setItemLabelGenerator(Category::name);
        binder.bind(category, Exercise::getCategory, Exercise::setCategory);
        addFormItem(category, "Category");

        description.setPlaceholder("Description...");
        binder.bind(description, Exercise::getDescription, Exercise::setDescription);
        addFormItem(description, "Description");
    }

    @NotNull
    @Contract(" -> new")
    private HorizontalLayout setupButtons() {
        Button saveButton = new Button("Save", new Icon(VaadinIcon.CHECK_CIRCLE),
                e -> FormUtils.checkFormValidity(binder, this::save));
        Button deleteButton = new Button("Delete", new Icon(VaadinIcon.CLOSE_CIRCLE), e -> askConfirmationForDeletion());
        Button cancelButton = new Button("Cancel", new Icon(VaadinIcon.CLOSE), e -> cancel());

        saveButton.getElement().getStyle().set("color", "green");
        deleteButton.getElement().getStyle().set("color", "red");
        cancelButton.getElement().getStyle().set("color", "orange");

        return new HorizontalLayout(saveButton, deleteButton, cancelButton);
    }

    private void save() {
        LOGGER.info(() -> "Saving " + exercise);
        exerciseRepository.save(exercise);
        LOGGER.info(() -> "Saved " + exercise);
        publishUpdateEvent();
        setExercise(null);
    }

    private void askConfirmationForDeletion() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirm = new Button("Confirm deletion of Exercise " + exercise.getName(), e -> {
            delete();
            dialog.close();
        });
        Button cancel = new Button("Cancel", e -> dialog.close());
        dialog.add(confirm, cancel);
        dialog.open();
    }

    private void delete() {
        LOGGER.info(() -> "Deleting " + exercise);
        exerciseRepository.delete(exercise);
        LOGGER.info(() -> "Deleted " + exercise);
        publishUpdateEvent();
        setExercise(null);
    }

    private void cancel() {
        setExercise(null);
    }

    private void publishUpdateEvent() {
        LOGGER.debug(() -> "Publishing event");
        eventPublisher.publishEvent(new ExerciseUpdateEvent(this));
    }

    void setExercise(Exercise exercise) {
        this.exercise = exercise;
        binder.setBean(exercise);

        manageNullExercise();
    }

    private void manageNullExercise() {
        boolean enabled = exercise != null;
        setEnabled(enabled);
        if (enabled) {
            name.focus();
        }
    }
}
