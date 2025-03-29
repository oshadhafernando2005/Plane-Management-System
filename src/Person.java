public class Person {
    private String name;
    private String surname;
    private String email;

    //constructor
    public Person(String name, String surname, String email) {

        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    // All getter and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    // method for Display passenger details
    public String printPerson() {
        return ("\nPerson Information ," +
                "\n\t\tName        : " + getName() +
                "\n\t\tSurName     : " + getSurname() +
                "\n\t\tEmail       : " + getEmail());
    }
}
