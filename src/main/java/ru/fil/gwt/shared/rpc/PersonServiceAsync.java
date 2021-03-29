package ru.fil.gwt.shared.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.fil.gwt.shared.dto.PersonDto;

import java.util.List;

public interface PersonServiceAsync {
    void list(String s, AsyncCallback<List<PersonDto>> callback);
    void add(PersonDto person, AsyncCallback<PersonDto> callback);
    void delete(PersonDto person, AsyncCallback<PersonDto> callback);
    void update(PersonDto person, AsyncCallback<PersonDto> callback);
}
