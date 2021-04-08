package com.To_Do_List.repository;

import com.To_Do_List.domain.Item;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgresItemRepository implements ItemRepository{

    private SqlSession sqlSession;

    @Autowired
    public PostgresItemRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int createItem(Item item) {
        return sqlSession.insert("Item.CreateItem", item);
    }

    @Override
    public int updateItem(Item item) {
        return sqlSession.update("Item.UpdateItem", item);
    }

    @Override
    public int deleteItem(Item item) {
        return sqlSession.delete("Item.DeleteItem", item);
    }

    @Override
    public int checkUserExist(String userNick) {
        return sqlSession.selectOne("Item.CheckUserExist", userNick);
    }

    @Override
    public List<Item> findItemByUserNick(String userNick) {
        return sqlSession.selectList("Item.FindItemByUserNick", userNick);
    }

    @Override
    public int checkItemExist(long id) {
        return sqlSession.selectOne("Item.CheckItemExist", id);
    }

    @Override
    public Item findItemById(long id) {
        return sqlSession.selectOne("Item.FindItemById", id);
    }

    @Override
    public List<Item> findAll() {
        return sqlSession.selectList("Item.FindItems");
    }
}
