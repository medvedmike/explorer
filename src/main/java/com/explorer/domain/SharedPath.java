package com.explorer.domain;


import javax.persistence.*;

/**
 * Created by Michael on 15.07.2014.
 */
@Entity
@Table(name = "shared_paths")
public class SharedPath {

    public static final String idColumn = "id";
    public static final String pathColumn = "path";
    public static final String userColumn = "user_id";

    @Id
    @Column(name = idColumn)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = pathColumn, nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = userColumn, nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
