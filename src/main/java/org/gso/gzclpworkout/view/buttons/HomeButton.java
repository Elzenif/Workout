package org.gso.gzclpworkout.view.buttons;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.gso.gzclpworkout.view.MainView;

public class HomeButton extends NavigationButton {

    public HomeButton() {
        super(new Icon(VaadinIcon.HOME), MainView.class);
    }
}
