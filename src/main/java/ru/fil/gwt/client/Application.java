package ru.fil.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import ru.fil.gwt.client.widgets.DialogAddOrRefactorPerson;
import ru.fil.gwt.shared.dto.PersonDto;
import ru.fil.gwt.shared.rpc.PersonService;
import ru.fil.gwt.shared.rpc.PersonServiceAsync;

import java.util.List;

public class Application implements EntryPoint {

    private PersonServiceAsync personServiceAsync = GWT.create(PersonService.class);

    @Override
    public void onModuleLoad() {
        CellTable<PersonDto> table = new CellTable<>();
        ListDataProvider<PersonDto> dataProvider = createTable(table);

        Button add = new Button("Add", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                DialogAddOrRefactorPerson dialogPerson = new DialogAddOrRefactorPerson();
                dialogPerson.addCloseHandler(new CloseHandler<PopupPanel>() {
                    @Override
                    public void onClose(CloseEvent<PopupPanel> closeEvent) {
                        PersonDto person = dialogPerson.getPerson();
                        if (person != null) {
                            personServiceAsync.add(person, new AsyncCallback<PersonDto>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                    Window.alert(throwable.toString());
                                }

                                @Override
                                public void onSuccess(PersonDto person) {
                                    dataProvider.getList().add(person);
                                }
                            });
                        }
                    }
                });
            }
        });
        add.setWidth("100px");

        Button delete = new Button("Delete", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final int index = table.getKeyboardSelectedRow();
                PersonDto person = dataProvider.getList().get(index);
                AsyncCallback<PersonDto> callback = new AsyncCallback<PersonDto>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(PersonDto person) {
                        dataProvider.getList().remove(index);
                    }
                };
                personServiceAsync.delete(person, callback);
            }
        });
        delete.setWidth("100px");

        Button refactor = new Button("Refactor", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                final int index = table.getKeyboardSelectedRow();
                PersonDto personDto = dataProvider.getList().get(index);
                DialogAddOrRefactorPerson dialogPerson = new DialogAddOrRefactorPerson(
                        personDto.getFirstName(),
                        personDto.getMiddleName(),
                        personDto.getLastName());
                dialogPerson.addCloseHandler(new CloseHandler<PopupPanel>() {
                    @Override
                    public void onClose(CloseEvent<PopupPanel> closeEvent) {
                        PersonDto person = dialogPerson.getPerson();
                        person.setId(personDto.getId());
                        AsyncCallback<PersonDto> callback = new AsyncCallback<PersonDto>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert(throwable.toString());
                            }

                            @Override
                            public void onSuccess(PersonDto person) {
                                dataProvider.getList().set(
                                        index,
                                        person
                                );
                            }
                        };
                        personServiceAsync.update(person, callback);
                    }
                });
            }
        });
        refactor.setWidth("100px");


        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(table);

        VerticalPanel vPanel2 = new VerticalPanel();
        vPanel2.add(add);
        vPanel2.add(delete);
        vPanel2.add(refactor);

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(vPanel);
        hPanel.add(vPanel2);
        hPanel.addStyleName("wrapper");

        RootPanel.get().add(hPanel);
    }

    private void deleteRpcRequest() {
    }

    private ListDataProvider<PersonDto> createTable(CellTable<PersonDto> table) {
        TextColumn<PersonDto> firstNameColumn = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto personDto) {
                return personDto.getFirstName();
            }
        };
        TextColumn<PersonDto> middleNameColumn = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto personDto) {
                return personDto.getMiddleName();
            }
        };
        TextColumn<PersonDto> lastNameColumn = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto personDto) {
                return personDto.getLastName();
            }
        };

        table.addColumn(firstNameColumn, "Firstname");
        table.addColumn(middleNameColumn, "Middlename");
        table.addColumn(lastNameColumn, "Lastname");

        ListDataProvider<PersonDto> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);

        personServiceAsync.list("", new AsyncCallback<List<PersonDto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(List<PersonDto> people) {
                dataProvider.getList().addAll(people);
            }
        });
        return dataProvider;
    }
}
