package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User() {}

    public User(int id, String name, String email, String password) {
        this.id = id; this.name = name; this.email = email; this.password = password;
    }

    // Getters and setters...
}
