package com.explorer.domain;

import javax.persistence.*;

/**
 * Created by Michael on 09.07.2014.
 */
@Entity
@Table(name = "roles")
public class Role {
    public static final String idColumn = "id";
    public static final String roleColumn = "role";

    @Id
    @Column(name = idColumn)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = roleColumn, nullable = false, unique = true)
    private String role;
}
