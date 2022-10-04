package com.lc.respository;

import com.lc.model.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author DELL
 * @date 2022/6/14 22:33
 */
public class InMemoryToDoRepository implements ToDoRepository {
    //线程安全的标识符序列号
    private AtomicLong currentId = new AtomicLong();
    /**
     * 用来保护to-do项目的有效内存结构
     */
    private ConcurrentMap<Long, ToDoItem> toDos = new
            ConcurrentHashMap<>();

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<>(toDos.values());
        //通过标识符对to-do项目进行排序
        Collections.sort(toDoItems);
        return toDoItems;
    }

    @Override
    public List<ToDoItem> findAllActive() {
        List<ToDoItem> activeToDos = new ArrayList<>();

        synchronized (toDos) {
            for (ToDoItem toDoItem : toDos.values()) {
                if (!toDoItem.isCompleted()) {
                    activeToDos.add(toDoItem);
                }
            }
        }
        return activeToDos;
    }

    @Override
    public List<ToDoItem> findAllCompleted() {
        List<ToDoItem> completedToDos = new ArrayList<>();

        synchronized (toDos) {
            for (ToDoItem toDoItem : toDos.values()) {
                if (toDoItem.isCompleted()) {
                    completedToDos.add(toDoItem);
                }
            }
        }
        return completedToDos;
    }

    @Override
    public ToDoItem findById(Long id) {
        return toDos.get(id);
    }

    @Override
    public Long insert(ToDoItem toDoItem) {
        Long id = currentId.incrementAndGet();
        toDoItem.setId(id);
        //如果to-do项目不存在，就只将它放到Map中
        toDos.putIfAbsent(id, toDoItem);
        return id;
    }

    @Override
    public void update(ToDoItem toDoItem) {
        //如果存在于Map中，就替换该项目
        toDos.replace(toDoItem.getId(), toDoItem);
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        //如果存在于Map中，就移除该项目
        toDos.remove(toDoItem.getId());
    }
}
