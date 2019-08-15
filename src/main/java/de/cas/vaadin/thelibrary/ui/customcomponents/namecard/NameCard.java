package de.cas.vaadin.thelibrary.ui.customcomponents.namecard;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class NameCard extends CustomComponent {
    private Label title, description;
    private boolean isDropdownSet;
    private HorizontalLayout nameCard;
    private VerticalLayout imageHolder, textHolder;

    public NameCard(String title, String description){
        this.title = new Label();
        this.description = new Label();
        imageHolder = new VerticalLayout();
        textHolder = new VerticalLayout();
        isDropdownSet = false;
        nameCard = new HorizontalLayout();
        setTitle(title);
        this.title.setStyleName(ValoTheme.LABEL_BOLD);
        setDescription(description);
        textHolder.addComponents(this.title, this.description);
        nameCard.addComponents(imageHolder, textHolder);

        setCompositionRoot(nameCard);
    }

    public void setImage(String fileName){
        Image img = new Image("", new ImageResource(fileName));
        img.setWidth("120px");
        img.setHeight("120px");
        imageHolder.addComponent(img);
    }

    public void setTitle(String str){
        title.setValue(str);
    }

    public void setDescription(String str){
        description.setValue(str);
    }

    public void setDropdown(boolean b){
        isDropdownSet = b;
        if(isDropdownSet){
            Button button = new DropdownButton();
            imageHolder.addComponent(button);
        }
    }

    public void buildComponent(){

    }


}
