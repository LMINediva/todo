package com.lc.web

import geb.Page

/**
 * @author DELL
 * @date 2022/7/7 10:03
 * @description
 */
class ToDoInsert extends Page {
    static url = "http://localhost:8080/todo/insert"
    static at = { title == "待办事项应用" }
}
