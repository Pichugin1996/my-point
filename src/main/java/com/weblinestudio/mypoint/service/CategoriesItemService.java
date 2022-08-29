package com.weblinestudio.mypoint.service;

import com.weblinestudio.mypoint.entity.business.CategoriesItem;
import com.weblinestudio.mypoint.entity.business.Item;
import com.weblinestudio.mypoint.entity.user.User;
import com.weblinestudio.mypoint.repository.CategoriesItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoriesItemService {

    private final CategoriesItemRepository categoriesItemRepository;
    private final UserService userService;

    @Autowired
    public CategoriesItemService(CategoriesItemRepository categoriesItemRepository, UserService userService) {
        this.categoriesItemRepository = categoriesItemRepository;
        this.userService = userService;
    }

    public void saveCategory(CategoriesItem categoriesItem, String username) {
        User userByUsername = userService.getUserByUsername(username);
        if (categoriesItem.getStatus() == null) {
            categoriesItem.setStatus(CategoriesItem.Status.ACTIVE);
        }
        if (categoriesItem.getIdUser() == null) {
            categoriesItem.setIdUser(userByUsername.getId());
        }
        categoriesItemRepository.save(categoriesItem);
    }

    public List<CategoriesItem> getCategoriesByUsername(String username) {
        log.debug("getCategoriesByUsername -- start, username: {}", username);

        Long usernameId = userService.getUserByUsername(username).getId();
        log.debug("getCategoriesByUsername -- getUserByUsername, usernameId: {}", usernameId);

        List<Optional<CategoriesItem>> optionalsList = categoriesItemRepository.findByIdUser(usernameId);
        log.debug("getCategoriesByUsername -- optionalsList: {}", optionalsList);

        if (!optionalsList.isEmpty()) {
            ArrayList<CategoriesItem> list = optionalsList.stream().map(cat -> cat.get()).collect(Collectors.toCollection(ArrayList::new));
            Collections.sort(list, new Comparator<CategoriesItem>() {
                @Override
                public int compare(CategoriesItem o1, CategoriesItem o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            //Возращаем список задач, где статус равен ACTIVE
            log.debug("getCategoriesByUsername -- optionalsListStream: {}", list);
            return list.stream().filter(cat -> cat.getStatus() == CategoriesItem.Status.ACTIVE).toList();
        }
        return new ArrayList<>();
    }

    public CategoriesItem getCategoriesById(Long id, String username) {
        CategoriesItem categoriesItem = categoriesItemRepository.findById(id).orElse(new CategoriesItem());
        User userByUsername = userService.getUserByUsername(username);
        if (categoriesItem.getIdUser() == userByUsername.getId()) {
            return categoriesItem;
        } else {
            return new CategoriesItem();
        }
    }

    public void deleteCategoriesById(Long id, String username) {
        CategoriesItem categoriesById = getCategoriesById(id, username);
        User userByUsername = userService.getUserByUsername(username);
        if (categoriesById.getIdUser() == userByUsername.getId()) {
            categoriesById.setStatus(CategoriesItem.Status.DELETE);
            categoriesItemRepository.save(categoriesById);
        }
    }
}
















