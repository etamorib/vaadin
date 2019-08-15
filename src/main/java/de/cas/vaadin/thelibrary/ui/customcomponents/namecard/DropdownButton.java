package de.cas.vaadin.thelibrary.ui.customcomponents.namecard;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

class DropdownButton extends Button {

    private static final Resource downIcon = VaadinIcons.ANGLE_DOWN;
    private static final Resource upIcon = VaadinIcons.ANGLE_UP;

    DropdownButton(){
        setIcon(downIcon);
    }
}
