package com.weblinestudio.mypoint.controllers.business;

import com.weblinestudio.mypoint.dto.ItemRequestDto;
import com.weblinestudio.mypoint.entity.business.Item;
import com.weblinestudio.mypoint.service.ItemsService;
import com.weblinestudio.mypoint.validate.ItemFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ItemsController {

    private final ItemsService itemsService;
    private final ItemFormValidator itemFormValidator;

    @Autowired
    public ItemsController(ItemsService itemsService, ItemFormValidator itemFormValidator) {
        this.itemsService = itemsService;
        this.itemFormValidator = itemFormValidator;
    }

    @GetMapping(value = "/storage/items")
    public String getItemsPage(Principal principal, Model model) {
        log.debug("getItemsPage -- start, principal: {}", principal);

        model.addAttribute("username", principal.getName());
        List<Item> tasksByUsername = itemsService.getTasksByUsername(principal.getName());
        model.addAttribute("items", itemsService.getTasksByUsername(principal.getName())
                //List ItemRequestDto
                .stream().map(item -> new ItemRequestDto().transformInDto(item)).collect(Collectors.toList()));

        return "/business/items";
    }

    @GetMapping("/storage/edit/{id}")
    public String getEditorPageWithId(@PathVariable Long id, Principal principal, Model model) {
        log.debug("getEditorPageWithId -- start, principal: {}, id: {}", principal, id);
        if (id != null) {
            model.addAttribute("item", new ItemRequestDto().transformInDto(itemsService.getItemById(id, principal.getName())));
        }
        model.addAttribute("username", principal.getName());

        return "/business/editor";
    }

    @PostMapping("/storage/delete/{id}")
    public String deleteItem(@PathVariable Long id, Principal principal, Model model) {
        log.debug("deleteItem -- start, principal: {}, id: {}", principal, id);
        if (id != null) {
            itemsService.deleteItemById(id, principal.getName());
        }
        model.addAttribute("username", principal.getName());
        return "redirect:/storage/items";
    }

    @GetMapping("/storage/edit")
    public String getEditorPage(Principal principal, Model model) {
        log.debug("getEditorPage -- start, principal: {}", principal);
        model.addAttribute("username", principal.getName());
        model.addAttribute("item", new ItemRequestDto());

        return "/business/editor";
    }

    @PostMapping("/storage/edit")
    public String postEditorPage(@ModelAttribute ItemRequestDto item,
                                 Model model,
                                 Principal principal,
                                 BindingResult bindingResult) {
        log.debug("postEditorPage -- start, principal: {}, item: {}", principal, item);
        itemFormValidator.verify(item, bindingResult);
        itemsService.saveItem(item.transformInItem(), principal.getName());

        model.addAttribute("username", principal.getName());
        model.addAttribute("item", item);
        model.addAttribute("save", "* Сохранено!");
        return "/business/editor";
    }


}
