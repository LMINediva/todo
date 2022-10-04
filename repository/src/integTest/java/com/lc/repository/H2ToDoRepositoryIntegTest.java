package com.lc.repository;

import com.lc.model.ToDoItem;
import com.lc.respository.H2ToDoRepository;
import com.lc.respository.ToDoRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author DELL
 * @date 2022/7/5 21:49
 */
public class H2ToDoRepositoryIntegTest {
    private ToDoRepository h2ToDoRepository;

    @Before
    public void setUp() {
        h2ToDoRepository = new H2ToDoRepository();
    }

    @Test
    public void testInsertToDoItem() {
        ToDoItem newToDoItem = new ToDoItem();
        newToDoItem.setName("编写集成测试");
        Long newId = h2ToDoRepository.insert(newToDoItem);
        newToDoItem.setId(newId);
        assertNotNull(newId);

        ToDoItem persistedToDoItem = h2ToDoRepository.findById(newId);
        assertNotNull(persistedToDoItem);
        assertEquals(newToDoItem, persistedToDoItem);
    }
}
