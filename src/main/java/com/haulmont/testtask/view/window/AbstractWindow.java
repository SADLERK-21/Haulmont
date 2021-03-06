package com.haulmont.testtask.view.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

abstract public class AbstractWindow extends Window {
    VerticalLayout main = new VerticalLayout();
    HorizontalLayout buttons = new HorizontalLayout();

    Button okButton = new Button("OK");
    Button cancelButton = new Button("Cancel");

    void configBasicLayout() {
        this.buttons.setSpacing(true);
        this.buttons.setMargin(true);

        this.buttons.addComponent(this.okButton);
        this.buttons.addComponent(this.cancelButton);

        this.main.setMargin(true);
        this.main.setSpacing(true);

        center();
        setModal(true);
        setClosable(false);
        setResizable(false);
    }

    void initCancelButton() {
        this.cancelButton.addClickListener((Button.ClickListener) clickEvent -> this.close());
    }
}
