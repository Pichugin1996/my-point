package com.weblinestudio.mypoint.repository;

import com.weblinestudio.mypoint.entity.business.CategoriesItem;
import com.weblinestudio.mypoint.entity.business.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesItemRepository extends JpaRepository<CategoriesItem, Long> {

    List<Optional<CategoriesItem>> findByIdUser(Long userId);
}
