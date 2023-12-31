package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

public class User {
    private Long ID;
    private String email;
    private String name;
    private String phoneNumber;

    public User(Long ID, String email, String name, String phoneNumber) {
        this.ID = ID;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
