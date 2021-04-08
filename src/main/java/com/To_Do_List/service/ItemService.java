package com.To_Do_List.service;

import com.To_Do_List.domain.Item;
import com.To_Do_List.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    public enum ERROR_MESSAGE {
        SUCCESS(1), NOT_EXIST_USERNICK(-1), NOT_EXIST_ITEM(-2);
        ERROR_MESSAGE(long value) {this.value = value;}

        private final long value;
        public long value() { return value;}
    };

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public long create(Item item){
        if(itemRepository.checkUserExist(item.getUserNick()) <= 0)
            return ERROR_MESSAGE.NOT_EXIST_USERNICK.value();

        itemRepository.createItem(item);

        return ERROR_MESSAGE.SUCCESS.value();
    }

    public long update(Item item){
        if(itemRepository.checkUserExist(item.getUserNick()) <= 0)
            return ERROR_MESSAGE.NOT_EXIST_USERNICK.value();

        itemRepository.updateItem(item);

        return ERROR_MESSAGE.SUCCESS.value();
    }

    public long delete(Item item){
        if(itemRepository.checkUserExist(item.getUserNick()) <= 0)
            return ERROR_MESSAGE.NOT_EXIST_USERNICK.value();

        if(itemRepository.checkItemExist(item.getId()) <= 0)
            return ERROR_MESSAGE.NOT_EXIST_ITEM.value();

        itemRepository.deleteItem(item);

        return ERROR_MESSAGE.SUCCESS.value();
    }

    public int checkUserExist(String userNick) {
        return itemRepository.checkUserExist(userNick);
    }

    public int checkItemExist(long id){
        return itemRepository.checkItemExist(id);
    }

    public Item findItemById(long id){
        if(itemRepository.checkItemExist(id) <= 0)
            return null;

        return itemRepository.findItemById(id);
    }


    public List<Item> findItemByUserNick(String userNick){
        if(itemRepository.checkUserExist(userNick) <= 0)
            return null;

        return itemRepository.findItemByUserNick(userNick);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }
}
