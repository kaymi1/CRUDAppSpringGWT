package ru.fil.gwt.shared.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.fil.gwt.shared.dto.PersonDto;

import java.util.List;

@RemoteServiceRelativePath("rpc/people")
public interface PersonService extends RemoteService {
    List<PersonDto> list(String s);
    PersonDto add(PersonDto person);
    PersonDto update(PersonDto person);
    void delete(PersonDto person);
}
