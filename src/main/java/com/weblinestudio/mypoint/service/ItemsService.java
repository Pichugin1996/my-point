package com.weblinestudio.mypoint.service;

import com.weblinestudio.mypoint.dto.ItemRequestDto;
import com.weblinestudio.mypoint.entity.business.Item;
import com.weblinestudio.mypoint.entity.user.User;
import com.weblinestudio.mypoint.repository.ItemsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final UserService userService;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository, UserService userService) {
        this.itemsRepository = itemsRepository;
        this.userService = userService;
    }

    public List<Item> getTasksByUsername(String username) {
        log.debug("getTasksByUsername -- start, username: {}", username);

        Long usernameId = getUserFromRepository(username).getId();
        log.debug("getTasksByUsername -- getUserByUsername, usernameId: {}", usernameId);
        List<Optional<Item>> optionalsList = itemsRepository.findByUserId(usernameId);
        log.debug("getTasksByUsername -- optionalsList: {}", optionalsList);

        if (!optionalsList.isEmpty()) {
            ArrayList<Item> list = optionalsList.stream().map(item -> item.get()).collect(Collectors.toCollection(ArrayList::new));
            Collections.sort(list, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            //Возращаем список задач, где статус равен ACTIVE
            log.debug("getTasksByUsername -- optionalsListStream: {}", list);
            return list.stream().filter(item -> item.getStatus() == Item.Status.ACTIVE).toList();
        }
        return new ArrayList<>();
    }

    public Item saveItem(Item item, String username) {
        log.debug("saveItem -- start, username: {}, item: {}", username, item);
        Long id = getUserFromRepository(username).getId();
        log.debug("saveItem -- getUserByUsername, id: {}", id);

        if (item.getCreationDate() == null) {
            log.debug("saveItem -- Timestamp, timeStamp: {}", Timestamp.valueOf(LocalDateTime.now()));
            item.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        }
        item.setStatus(Item.Status.ACTIVE);
        item.setUserId(id);

        log.debug("saveItem -- save, item: {}", item);
        Item save = itemsRepository.save(item);
        log.debug("saveItem -- end}");
        return save;
    }

    public Item getItemById(Long id, String username) {
        log.debug("getItemById -- start, id: {}", id);
        Item item = itemsRepository.findById(id).orElse(new Item());
        Long idUser = getUserFromRepository(username).getId();
        if (item.getUserId() != idUser) {
            log.debug("getItemById -- getUserFromRepository: error, idUserIdItem: {}, userId: {}", item.getUserId(), idUser);
            return new Item();
        }
        log.debug("getItemById -- return, item: {}", item);
        return item;
    }

    public void deleteItemById(Long id, String username) {
        log.debug("deleteItemById -- start, idItem: {}, username: {}", id, username);
        Item itemById = getItemById(id, username);
        if (itemById.getId() != null) {
            itemById.setStatus(Item.Status.DELETE);
            itemsRepository.save(itemById);
        }
    }

    private User getUserFromRepository(String username) {
        return userService.getUserByUsername(username);
    }
}
