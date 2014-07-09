package com.explorer.domain;

//import org.hibernate.annotations.Entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Michael on 08.07.2014.
 */
@Entity
@Table(name = "users")
public class User {

    public static final String idColumn = "id";
    public static final String usernameColumn = "username";
    public static final String passwordColumn = "password";
    public static final String homeColumn = "home";

    @Id
    @Column(name = idColumn)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = usernameColumn, unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "inputError.login.length")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "inputError.login.letters")
    private String username;

    @Column(name = passwordColumn, nullable = false)
    @Size(min = 6, max = 20, message = "inputError.password.length")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
