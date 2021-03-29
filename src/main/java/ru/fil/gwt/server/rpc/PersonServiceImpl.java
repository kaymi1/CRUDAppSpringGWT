package ru.fil.gwt.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fil.gwt.shared.rpc.PersonService;
import ru.fil.gwt.server.dao.PersonDao;
import ru.fil.gwt.server.domain.Person;
import ru.fil.gwt.shared.dto.PersonDto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("GwtServiceNotRegistered")
@Component
public class PersonServiceImpl extends RemoteServiceServlet implements PersonService {

    @Autowired
    private PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao){
        this.personDao = personDao;
    }

    @Override
    public List<PersonDto> list(String s) {
        List<PersonDto> people = new ArrayList<>();
        List<Person> peopleFromDB = personDao.findAll();
        for (Person person : peopleFromDB) {
            PersonDto personDto = new PersonDto(
                    person.getId(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName());
            people.add(personDto);
        }
        return people;
    }

    @Override
    public PersonDto update(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setMiddleName(personDto.getMiddleName());
        person.setLastName(personDto.getLastName());
        person.setId(personDto.getId());

        personDao.update(person);
        personDto.setId(person.getId());
        return personDto;
    }

    @Override
    public PersonDto add(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setMiddleName(personDto.getMiddleName());
        person.setLastName(personDto.getLastName());

        personDao.add(person);
        personDto.setId(person.getId());
        return personDto;
    }

    @Override
    public void delete(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setMiddleName(personDto.getMiddleName());
        person.setLastName(personDto.getLastName());
        person.setId(personDto.getId());

        personDao.delete(person);
    }
}
