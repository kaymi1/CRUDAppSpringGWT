package ru.fil.gwt.client.widgets;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import ru.fil.gwt.shared.dto.PersonDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DialogAddOrRefactorPerson extends DialogBox {

    private PersonDto person;
    private List<TextBox> textBoxList = new ArrayList<>();
    private HashMap<TextBox, String> mapLabel = new HashMap<>();
    private TextBox firstNameBox = new TextBox();
    private TextBox middleNameBox = new TextBox();
    private TextBox lastNameBox = new TextBox();

    public DialogAddOrRefactorPerson() {
        init();
        DialogAddOrRefactorPerson.this.center();
        DialogAddOrRefactorPerson.this.show();
    }

    public DialogAddOrRefactorPerson(String firstName, String middleName, String lastName) {
        this.firstNameBox.setText(firstName);
        this.middleNameBox.setText(middleName);
        this.lastNameBox.setText(lastName);
        init();
        DialogAddOrRefactorPerson.this.center();
        DialogAddOrRefactorPerson.this.show();
    }

    private void constructValueChangeHandler(TextBox textBox){
        textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> valueChangeEvent) {
                String input = textBox.getText().trim();
                if (!input.matches("^[0-9a-zA-Z\\.]{0,50}$")) {
                    Window.alert("'" + input + "' is not a valid string.");
                    textBox.setText("");
                    return;
                }
            }
        });
    }

    private void init(){
        VerticalPanel mainPanel = new VerticalPanel();
        setText("Add all properties for person");
        setAnimationEnabled(true);
        setGlassEnabled(true);

        textBoxList = Arrays.asList(
                this.firstNameBox,
                this.middleNameBox,
                this.lastNameBox);

        mapLabel.put(firstNameBox, "Firstname");
        mapLabel.put(middleNameBox, "Middlename");
        mapLabel.put(lastNameBox, "Lastname");

        for (TextBox textBox : textBoxList) {
            constructValueChangeHandler(textBox);

            HorizontalPanel tbPanel = new HorizontalPanel();
            Label tbLabel = new Label(mapLabel.get(textBox));
            tbLabel.setWidth("100px");
            tbPanel.add(tbLabel);
            tbPanel.add(textBox);
            mainPanel.add(tbPanel);
        }

        Button save = new Button("Save");
        save.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PersonDto person = new PersonDto(
                        getFirstNameBox().getText(),
                        getMiddleNameBox().getText(),
                        getLastNameBox().getText());
                DialogAddOrRefactorPerson.this.setPerson(person);
                DialogAddOrRefactorPerson.this.hide();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                DialogAddOrRefactorPerson.this.hide();
            }
        });

        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(save);
        buttonsPanel.add(cancel);

        mainPanel.add(buttonsPanel);
        setWidget(mainPanel);
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public TextBox getFirstNameBox() {
        return firstNameBox;
    }

    public void setFirstNameBox(TextBox firstNameBox) {
        this.firstNameBox = firstNameBox;
    }

    public TextBox getMiddleNameBox() {
        return middleNameBox;
    }

    public void setMiddleNameBox(TextBox middleNameBox) {
        this.middleNameBox = middleNameBox;
    }

    public TextBox getLastNameBox() {
        return lastNameBox;
    }

    public void setLastNameBox(TextBox lastNameBox) {
        this.lastNameBox = lastNameBox;
    }
}
