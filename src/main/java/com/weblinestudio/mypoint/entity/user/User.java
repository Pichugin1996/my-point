package com.weblinestudio.mypoint.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mp_users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 70)
    private String email;

    @Column(name = "username", unique = true, nullable = false, length = 70)
    private String username;

    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @Column(name = "firstName", length = 70)
    private String firstName;

    @Column(name = "lastName", length = 70)
    private String lastName;

    @Column(name = "phone", unique = true, nullable = false, length = 15)
    private String phone;

    //TODO remake
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "mp_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "active")
    private boolean active;
}
