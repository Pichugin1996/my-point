package com.weblinestudio.mypoint.dto;

import com.weblinestudio.mypoint.entity.business.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    private Long id;
    private Long userId;
    private String titleName;
    private String category;
    private BigDecimal price;
    private Long amount;
    private Double weight;
    private String creationDate;
    private Item.Status status;

    public ItemRequestDto transformInDto(Item item) {
        this.id = item.getId();
        this.userId = item.getUserId();
        this.titleName = item.getTitleName();
        this.category = item.getCategory();
        this.price = item.getPrice();
        this.amount = item.getAmount();
        this.weight = item.getWeight();
//        this.creationDate = item.getCreationDate().toString();
        if (item.getCreationDate() != null) {
            this.creationDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(item.getCreationDate());
        } else {
            this.creationDate = null;
        }
        this.status = item.getStatus();
        return this;
    }

    public Item transformInItem() {
        Item item = new Item();
        item.setId(id);
        item.setUserId(userId);
        item.setTitleName(titleName);
        item.setCategory(category);
        item.setPrice(price);
        item.setAmount(amount);
        item.setWeight(weight);
//        item.setCreationDate(Timestamp.valueOf(creationDate));
        System.out.println(creationDate);
        if (creationDate.length() > 5) {
            LocalDateTime parse = LocalDateTime.parse(creationDate, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
            item.setCreationDate(Timestamp.valueOf(parse));
        } else {
        item.setCreationDate(null);
        }
        item.setStatus(status);
        System.out.println(item);

        return item;
    }

}
