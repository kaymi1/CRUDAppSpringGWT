package ru.fil.gwt.shared.dto;

import java.io.Serializable;

public class PersonDto implements Serializable {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    protected PersonDto(){}

    public PersonDto(String firstName, String middleName, String lastName){
        this.id = -1L;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public PersonDto(Long id, String firstName, String middleName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getMiddleName(){
        return this.middleName;
    }
    public Long getId(){return this.id;}
    public void setId(Long value) { this.id = value;}
}
