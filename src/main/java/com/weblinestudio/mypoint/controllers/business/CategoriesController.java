package com.weblinestudio.mypoint.controllers.business;

import com.weblinestudio.mypoint.entity.business.CategoriesItem;
import com.weblinestudio.mypoint.service.CategoriesItemService;
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

@Controller
@Slf4j
public class CategoriesController {

    private final CategoriesItemService categoriesItemService;

    @Autowired
    public CategoriesController(CategoriesItemService categoriesItemService) {
        this.categoriesItemService = categoriesItemService;
    }

    @GetMapping(value = "/storage/categories")
    public String getCategoriesPage(Principal principal, Model model) {
        log.debug("getCategoriesPage -- start, principal: {}", principal);

        model.addAttribute("username", principal.getName());

        List<CategoriesItem> categoriesByUsername = categoriesItemService.getCategoriesByUsername(principal.getName());
        model.addAttribute("categories", categoriesByUsername);
        log.debug("getCategoriesPage -- start, categories: {}", categoriesByUsername);

        return "/business/categories";
    }

    @GetMapping("/storage/categories/edit/{id}")
    public String getEditorPageWithId(@PathVariable Long id, Principal principal, Model model) {
        log.debug("getEditorPageWithId -- start, id: {},  principal: {}", id, principal);
        model.addAttribute("username", principal.getName());
        if (id == null) {
            model.addAttribute("category", new CategoriesItem());
        }
        CategoriesItem categoriesById = categoriesItemService.getCategoriesById(id, principal.getName());
        model.addAttribute("category", categoriesById);
        log.debug("getEditorPageWithId -- end, categoriesById: {}", categoriesById);
        return "/business/editorCategories";
    }

    @GetMapping("/storage/categories/edit")
    public String getEditorPage(Principal principal, Model model) {
        log.debug("getEditorPage -- start, principal: {}", principal);
        model.addAttribute("username", principal.getName());
        model.addAttribute("category", new CategoriesItem());

        return "/business/editorCategories";
    }

    @PostMapping("/storage/categories/edit")
    public String postEditorPage(@ModelAttribute CategoriesItem category,
                                 Model model,
                                 Principal principal,
                                 BindingResult bindingResult) {
        log.debug("postEditorPage -- start, principal: {}, category: {}", principal, category);
        categoriesItemService.saveCategory(category, principal.getName());

        model.addAttribute("username", principal.getName());
        model.addAttribute("category", category);
        model.addAttribute("save", "* Сохранено!");
        return "/business/editorCategories";
    }

    @PostMapping("/storage/categories/edit/{id}")
    public String deleteEditorPageWithId(@PathVariable Long id, Principal principal, Model model) {
        log.debug("deleteEditorPageWithId -- start, id: {},  principal: {}", id, principal);
        model.addAttribute("username", principal.getName());
        if (id != null) {
            categoriesItemService.deleteCategoriesById(id, principal.getName());
        }
        return "redirect:/storage/categories";
    }
}







