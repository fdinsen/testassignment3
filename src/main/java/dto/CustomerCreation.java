package dto;

public class CustomerCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public final String firstname, lastname;

    public final String phone;

    public CustomerCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = null;
    }

    public CustomerCreation(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

}
