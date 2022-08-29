package com.weblinestudio.mypoint.entity.business;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mp_categories")
@Data
public class CategoriesItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category")
    private String categoriesName;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CategoriesItem.Status status;

    public static enum Status {
        ACTIVE, DELETE;
    }
}
