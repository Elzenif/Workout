package org.gso.gzclpworkout.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

class HomeButton extends Button {

    HomeButton() {
        super(new Icon(VaadinIcon.HOME));
        addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
    }
}
