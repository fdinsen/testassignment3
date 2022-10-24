package dto;

import java.util.Date;

public class EmployeeCreation {
    public final String firstname, lastname;
    public final Date birthdate;

    public EmployeeCreation(String firstname, String lastname, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }
}
