package ru.fil.gwt.server.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.fil.gwt.server.domain.Person;
import ru.fil.gwt.shared.dto.PersonDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Repository
public class PersonDao {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("people");

    public List<Person> findAll(){
        EntityManager em = entityManager();
        return (List<Person>) em.createQuery("select p from Person p").getResultList();
    }

    public void add(Person person){
        EntityManager em = entityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    public void update(Person person){
        EntityManager em = entityManager();
        em.getTransaction().begin();
        em.merge(person);
        em.getTransaction().commit();
    }

    public void delete(Person person){
        EntityManager em = entityManager();
        em.getTransaction().begin();
        Person person1 = em.find(Person.class, person.getId());
        em.remove(person1);
        em.getTransaction().commit();
    }



    public static EntityManager entityManager(){
        return emf.createEntityManager();
    }
}
