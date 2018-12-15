package org.gso.gzclpworkout.view.buttons;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;

abstract class NavigationButton extends Button {

    NavigationButton(Icon icon, Class<? extends Component> viewClass) {
        super(icon);
        addClickListener(e -> goTo(viewClass));
    }

    NavigationButton(String text, Class<? extends Component> viewClass) {
        super(text);
        addClickListener(e -> goTo(viewClass));
    }

    private void goTo(Class<? extends Component> navigationTarget) {
        getUI().ifPresent(ui -> ui.navigate(navigationTarget));
    }

}
