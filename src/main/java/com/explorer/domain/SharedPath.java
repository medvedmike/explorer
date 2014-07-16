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
    public static final String targetUserColumn = "target_user_id";
    public static final String sourceUserColumn = "source_user_id";

    @Id
    @Column(name = idColumn)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = pathColumn, nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = targetUserColumn, nullable = false)
    private User targetUser;

    @ManyToOne
    @JoinColumn(name = sourceUserColumn, nullable = false)
    private User sourceUser;

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

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public User getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(User sourceUser) {
        this.sourceUser = sourceUser;
    }
}
