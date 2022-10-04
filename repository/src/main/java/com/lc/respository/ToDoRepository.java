package com.lc.respository;

import com.lc.model.ToDoItem;

import java.util.List;

/**
 * @author DELL
 * @date 2022/6/14 22:31
 */
public interface ToDoRepository {
    /**
     * 查询所有待办事项
     *
     * @return 待办事项列表
     */
    List<ToDoItem> findAll();

    /**
     * 查询所有激活的代办事项
     *
     * @return 激活的待办事项列表
     */
    List<ToDoItem> findAllActive();

    /**
     * 查询所有完成的待办事项
     *
     * @return 完成的待办事项列表
     */
    List<ToDoItem> findAllCompleted();

    /**
     * 根据ID查询待办事项
     *
     * @param id 要查询的待办事项ID
     * @return 指定ID的待办事项列表
     */
    ToDoItem findById(Long id);

    /**
     * 新增一个待办事项
     *
     * @param toDoItem 要新增的待办事项列表
     * @return 插入代办事项的ID
     */
    Long insert(ToDoItem toDoItem);

    /**
     * 修改待办事项
     *
     * @param toDoItem 要修改的待办事项
     */
    void update(ToDoItem toDoItem);

    /**
     * 删除待办事项
     *
     * @param toDoItem 要删除的待办事项
     */
    void delete(ToDoItem toDoItem);
}
