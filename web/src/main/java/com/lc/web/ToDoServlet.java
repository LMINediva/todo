package com.lc.web;

import com.lc.model.ToDoItem;
import com.lc.respository.H2ToDoRepository;
import com.lc.respository.InMemoryToDoRepository;
import com.lc.respository.ToDoRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 * @date 2022/6/15 15:03
 */
public class ToDoServlet extends HttpServlet {
    public static final String FIND_ALL_SERVLET_PATH = "/all";
    public static final String INDEX_PAGE = "/jsp/todo-list.jsp";
    //private ToDoRepository toDoRepository = new InMemoryToDoRepository();
    private ToDoRepository toDoRepository = new H2ToDoRepository();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String servletPath = request.getServletPath();
        String view = processRequest(servletPath, request);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }

    /**
     * 为每个映射URL实现CRUD操作
     *
     * @param servletPath 请求路径
     * @param request     请求对象
     * @return 不满足任何一处处理方式返回默认路径
     */
    private String processRequest(String servletPath,
                                  HttpServletRequest request) {
        if (servletPath.equals(FIND_ALL_SERVLET_PATH)) {
            List<ToDoItem> toDoItems = toDoRepository.findAll();
            request.setAttribute("toDoItems", toDoItems);
            request.setAttribute("stats", determineStatus(toDoItems));
            request.setAttribute("filter", "all");
            return INDEX_PAGE;
        } else if (servletPath.equals("/active")) {
            List<ToDoItem> toDoItems = toDoRepository.findAll();
            request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, true));
            request.setAttribute("stats", determineStatus(toDoItems));
            request.setAttribute("filter", "active");
            return INDEX_PAGE;
        } else if (servletPath.equals("/completed")) {
            List<ToDoItem> toDoItems = toDoRepository.findAll();
            request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, false));
            request.setAttribute("stats", determineStatus(toDoItems));
            request.setAttribute("filter", "completed");
            return INDEX_PAGE;
        }
        //CRUD操作
        if (servletPath.equals("/insert")) {
            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setName(request.getParameter("name"));
            toDoRepository.insert(toDoItem);
            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/update")) {
            ToDoItem toDoItem = toDoRepository.findById(
                    Long.parseLong(request.getParameter("id")));
            if (toDoItem != null) {
                toDoItem.setName(request.getParameter("name"));
                toDoRepository.update(toDoItem);
            }
            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/delete")) {
            ToDoItem toDoItem = toDoRepository.findById(
                    Long.parseLong(request.getParameter("id")));
            if (toDoItem != null) {
                toDoRepository.delete(toDoItem);
            }
            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/toggleStatus")) {
            ToDoItem toDoItem = toDoRepository.findById(
                    Long.parseLong(request.getParameter("id")));
            if (toDoItem != null) {
                boolean completed = "on".equals(
                        request.getParameter("toggle")) ? true : false;
                toDoItem.setCompleted(completed);
                toDoRepository.update(toDoItem);
            }
            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/clearCompleted")) {
            List<ToDoItem> toDoItems = toDoRepository.findAll();
            for (ToDoItem toDoItem : toDoItems) {
                if (toDoItem.isCompleted()) {
                    toDoRepository.delete(toDoItem);
                }
            }
            return "/" + request.getParameter("filter");
        }

        return FIND_ALL_SERVLET_PATH;
    }

    /**
     * 基于状态的过滤器
     *
     * @param toDoItems 待办事项列表
     * @param active    是否激活
     * @return 基于状态的过滤后的待办事项列表
     */
    private List<ToDoItem> filterBasedOnStatus(List<ToDoItem> toDoItems,
                                               boolean active) {
        List<ToDoItem> filteredToDoItems = new ArrayList<>();
        for (ToDoItem toDoItem : toDoItems) {
            if (toDoItem.isCompleted() != active) {
                filteredToDoItems.add(toDoItem);
            }
        }
        return filteredToDoItems;
    }

    /**
     * 决定待办事项状态
     *
     * @param toDoItems 待办事项列表
     * @return 待办事项状态对象
     */
    private ToDoListStats determineStatus(List<ToDoItem> toDoItems) {
        ToDoListStats toDoListStats = new ToDoListStats();
        for (ToDoItem toDoItem : toDoItems) {
            if (toDoItem.isCompleted()) {
                toDoListStats.addCompleted();
            } else {
                toDoListStats.addActive();
            }
        }
        return toDoListStats;
    }

    /**
     * 待办事项列表状态
     */
    public class ToDoListStats {
        private int active;
        private int completed;

        private void addActive() {
            active++;
        }

        private void addCompleted() {
            completed++;
        }

        public int getActive() {
            return active;
        }

        public int getCompleted() {
            return completed;
        }

        public int getAll() {
            return active + completed;
        }
    }
}
