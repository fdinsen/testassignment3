package dto;

import java.util.Date;

public class Employee {
    private final int id;
    private String firstname, lastname;
    private Date birthdate;

    public Employee(int id, String firstname, String lastname, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
