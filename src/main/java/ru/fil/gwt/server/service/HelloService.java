package ru.fil.gwt.server.service;

import org.springframework.stereotype.Service;
import ru.fil.gwt.shared.dto.StringDto;

@Service
public class HelloService {

    public StringDto sayHello(StringDto targetName) {
        String name = targetName == null ? null : targetName.getValue();
        return new StringDto(sayHello(name));
    }

    public String sayHello(String targetName) {
        return "Hello dear " + (targetName == null ? "Unknown" : targetName);
    }

}
