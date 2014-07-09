package com.explorer.domain;

import javax.persistence.*;

/**
 * Created by Michael on 09.07.2014.
 */
@Entity
@Table(name = "authorities")
public class Authority {
    public static final String idColumn = "id";
    public static final String usernameColumn = "user_id";
    public static final String authorityColumn = "role_id";

    @Id
    @Column(name = idColumn)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @JoinColumn(name = authorityColumn, nullable = false)
    @ManyToOne
    private Role role;

    @JoinColumn(name = usernameColumn, nullable = false)
    @ManyToOne
    private User user;
}
