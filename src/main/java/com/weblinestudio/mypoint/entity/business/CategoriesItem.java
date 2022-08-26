package com.weblinestudio.mypoint.entity.business;

import javax.persistence.*;

@Entity
@Table(name = "mp_categories")
public class CategoriesItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category")
    private String categoriesName;

    @Column(name = "id_item")
    private Long idItem;

    @Column(name = "id_user")
    private Long idUser;
}
