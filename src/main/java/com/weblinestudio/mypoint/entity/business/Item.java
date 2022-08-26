package com.weblinestudio.mypoint.entity.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mp_items")
public class Item {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "name_item")
    private String titleName;

    @Column(name = "category_name")
    private String category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public static enum Status {
        ACTIVE, DELETE;
    }
}
