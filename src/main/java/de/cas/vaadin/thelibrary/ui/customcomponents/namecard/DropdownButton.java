package de.cas.vaadin.thelibrary.ui.customcomponents.namecard;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

public class DropdownButton extends Button {

    private static final Resource downIcon = VaadinIcons.ANGLE_DOWN;
    private static final Resource upIcon = VaadinIcons.ANGLE_UP;

    public DropdownButton(){
        setIcon(downIcon);
    }
}
