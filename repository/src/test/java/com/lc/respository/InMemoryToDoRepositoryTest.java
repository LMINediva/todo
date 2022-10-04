package com.lc.respository;

import com.lc.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author DELL
 * @date 2022/9/17 22:37
 */
public class InMemoryToDoRepositoryTest {
    private ToDoRepository inMemoryToDoRepository;

    @Before
    public void setUp() {
        inMemoryToDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void testInsertToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("编写单元测试");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
    }

    @Test
    public void testFindToDoItemById() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("编写单元测试");
        inMemoryToDoRepository.insert(toDoItem);
        ToDoItem foundToDoItem = inMemoryToDoRepository.findById(1L);
        assertEquals(toDoItem, foundToDoItem);
    }

    @Test
    public void testFindAllToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("编写单元测试");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("编写集成测试");
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("编写功能测试");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(3, toDoItems.size());
    }

    @Test
    public void testFindAllActiveToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("编写单元测试");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("编写集成测试");
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("编写功能测试");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAllActive();

        assertEquals(3, toDoItems.size());
    }

    @Test
    public void testFindAllCompletedToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("编写单元测试");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("编写集成测试");
        toDoItem2.setCompleted(true);
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("编写功能测试");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAllCompleted();

        assertEquals(1, toDoItems.size());
    }

    @Test
    public void testDeleteToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("编写单元测试");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
        inMemoryToDoRepository.delete(toDoItem);
        toDoItems = inMemoryToDoRepository.findAll();
        assertEquals(0, toDoItems.size());
    }

    @Test
    public void testUpdateToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("编写单元测试");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
        toDoItem.setName("更新一个条目");
        inMemoryToDoRepository.update(toDoItem);
        toDoItems = inMemoryToDoRepository.findAll();
        assertEquals("更新一个条目", toDoItems.get(0).getName());
    }
}
