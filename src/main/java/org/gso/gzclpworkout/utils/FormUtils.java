package org.gso.gzclpworkout.utils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;

public class FormUtils {

    public static void checkFormValidity(@NotNull Binder<?> binder, Runnable save) {
        if (binder.isValid()) {
            save.run();
        } else {
            String errorMessage = binder.validate().getFieldValidationStatuses()
                    .stream()
                    .filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(Optional::get)
                    .distinct()
                    .collect(Collectors.joining(", "));
            Notification.show(errorMessage, 10000, Notification.Position.TOP_CENTER);
        }
    }

}
