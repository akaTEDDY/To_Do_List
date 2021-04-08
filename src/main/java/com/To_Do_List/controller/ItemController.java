package com.To_Do_List.controller;

import com.To_Do_List.controller.form.ItemForm;
import com.To_Do_List.domain.Item;
import com.To_Do_List.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    // Item 추가
    @GetMapping("/item/create")
    public String createItem() {
        return "item/create";
    }

    @PostMapping("/item/create")
    public String createItemForm(ItemForm form, Model model) {
        Item item = new Item();
        item.setId(form.getId());
        item.setTitle(form.getTitle());
        item.setNote(form.getNote());
        item.setStatus(form.getStatus());
        item.setUserNick(form.getUserNick());

        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date regDate = new Date();
            Date dueDate = transFormat.parse(form.getDueDate());

            item.setRegDate(regDate);
            item.setDueDate(dueDate);
        } catch (ParseException e) {
            model.addAttribute("errorMsg", "DATE_PARSING_ERROR");
            return "/item/fail";
        }

        long result = itemService.create(item);

        if(result == ItemService.ERROR_MESSAGE.NOT_EXIST_USERNICK.value())
            model.addAttribute("errorMsg", "NOT_EXIST_USERNICK");

        if(result <= 0) {
            model.addAttribute("service", "생성");
            return "/item/fail";
        }

        return "redirect:/";
    }

    // Item 삭제
    @GetMapping("/item/delete")
    public String deleteItem() {
        return "item/delete";
    }

    @PostMapping("/item/delete")
    public String deleteItemForm(ItemForm form, Model model) {
        Item item = new Item();
        item.setId(form.getId());
        item.setUserNick(form.getUserNick());

        long result = itemService.delete(item);

        if(result == ItemService.ERROR_MESSAGE.NOT_EXIST_USERNICK.value())
            model.addAttribute("errorMsg", "NOT_EXIST_USERNICK");
        else if(result == ItemService.ERROR_MESSAGE.NOT_EXIST_ITEM.value())
            model.addAttribute("errorMsg", "NOT_EXIST_ITEM");

        if(result <= 0) {
            model.addAttribute("service", "삭제");
            return "/item/fail";
        }

        return "redirect:/";
    }

    // Item update
    @GetMapping("/item/update")
    public String updateItem() {
        return "/item/update";
    }

    @GetMapping("/item/update/{id}")
    public String updateItemForm(@PathVariable("id") String id, Model model) {
        if(id.isEmpty()) {
            model.addAttribute("errorMsg", "NOT_EXIST_ITEM");
            model.addAttribute("service", "수정");
            return "/item/fail";
        }

        Item item = itemService.findItemById(Long.parseLong(id));

        if(item == null) {
            model.addAttribute("errorMsg", "NOT_EXIST_ITEM");
            model.addAttribute("service", "수정");
            return "/item/fail";
        }

        model.addAttribute("item", item);

        return "/item/updateform";
    }

    @PostMapping("/item/update")
    public String updateItemForm(ItemForm form, Model model) {
        Item item = new Item();

        item.setId(form.getId());
        item.setTitle(form.getTitle());
        item.setNote(form.getNote());
        item.setStatus(form.getStatus());
        item.setUserNick(form.getUserNick());

        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dueDate = transFormat.parse(form.getDueDate());
            item.setDueDate(dueDate);
        } catch (ParseException e) {
            model.addAttribute("errorMsg", "DATE_PARSING_ERROR");
            return "/item/fail";
        }

        long result = itemService.update(item);

        if(result == ItemService.ERROR_MESSAGE.NOT_EXIST_USERNICK.value())
            model.addAttribute("errorMsg", "NOT_EXIST_USERNICK");

        if(result <= 0) {
            model.addAttribute("service", "수정");
            return "/item/fail";
        }

        return "redirect:/";
    }

    // Item FindAll
    @GetMapping("/item/items")
    public String findAll() {
        return "/item/items";
    }

    @GetMapping("/item/items/{nick}")
    public String findAllForm(@PathVariable("nick") String nick, Model model) {
        List<Item> items = itemService.findItemByUserNick(nick);

        if(items == null) {
            model.addAttribute("service", "조회");
            model.addAttribute("errorMsg", "NOT_EXIST_USERNICK");
            return "/item/fail";
        }

        model.addAttribute("items", items);

        return "item/itemsform";
    }
}
