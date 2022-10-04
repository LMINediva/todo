<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible"
          content="IE-edge,chrome=1">
    <title>待办事项应用</title>
    <script type="text/javascript" src="<c:url value="js/ext/jquery-3.6.0-min.js"/>"></script>
    <c:import url="app-js.jsp"/>
    <link rel="stylesheet" href="<c:url value="css/base.css"/>">
</head>
<body>
<section id="todoapp">
    <header id="header">
        <h1>代办事项</h1>
        <form action="<c:url value="insert"/>" method="post">
            <input type="hidden" name="filter" value="${filter}">
            <input id="new-todo" name="name"
                   placeholder="需要去做什么？" autofocus>
        </form>
    </header>
    <section id="main">
        <input id="toggle-all" type="checkbox">
        <label for="toggle-all">将所有待办事项标记为完成</label>
        <ul id="todo-list">
            <c:forEach var="toDoItem" items="${toDoItems}" varStatus="status">
                <li id="toDoItem_${status.count}"
                    class="<c:if test="${toDoItem.completed}">完成</c:if>"
                    ondblclick="javascript:editToDoItem(${status.count});">
                    <div class="view">
                        <form id="toggleForm_${status.count}" action="<c:url value="toggleStatus"/>" method="POST">
                            <input type="hidden" name="id" value="${toDoItem.id}"/>
                            <input type="hidden" name="filter" value="${filter}">
                            <input class="toggle" name="toggle" type="checkbox"
                                   <c:if test="${toDoItem.completed}">检查</c:if>
                                   onchange="javascript:document.getElementById('toggleForm_${status.count}').submit();">
                        </form>
                        <label>${toDoItem.name}</label>
                        <form action="<c:url value="delete"/>" method="POST">
                            <input type="hidden" name="id" value="${toDoItem.id}"/>
                            <input type="hidden" name="filter" value="${filter}"/>
                            <button class="destory">删除</button>
                        </form>
                    </div>
                    <form id="updateForm_${status.count}" action="<c:url value="update"/>" method="POST">
                        <input type="hidden" name="id" value="${toDoItem.id}"/>
                        <input type="hidden" name="filter" value="${filter}"/>
                        <input class="edit" id="toDoItemName_${status.count}" name="name"
                               value="${toDoItem.name}"
                               onblur="javascript:updateToDoItem(${status.count});"/>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </section>
    <footer id="footer">
        <c:if test="${stats.all} > 0">
            <span id="todo-count">
                <strong>
                <c:out value="${stats.active}"/>
                </strong>
                <c:choose>
                    <c:when test="${stats.active == 1}">
                        item
                    </c:when>
                    <c:otherwise>
                        items
                    </c:otherwise>
                </c:choose>
            左
            </span>
            <ul id="filters">
                <li>
                    <a
                    <c:if test="${filter == 'all'}">
                            class="selected" href="<c:url value="/all"/>">所有</a>
                    </c:if>
                </li>
                <li>
                    <a
                            <c:if test="${filter == 'active'}">class="selected"</c:if>
                            href="<c:url value="active"/>">激活</a>
                </li>
                <li>
                    <a
                            <c:if test="${filter == 'completed'}">class="selected"</c:if>
                            href="<c:url value="completed"/>">完成</a>
                </li>
            </ul>
            <c:if test="${stats.completed > 0}">
                <form action="<c:url value="clearCompleted"/>" method="POST">
                    <input type="hidden" name="filter" value="${filter}"/>
                    <button id="clear-completed">
                        清除完成(<c:out value="${stats.completed}"/>)
                    </button>
                </form>
            </c:if>
        </c:if>
    </footer>
</section>
<div id="info">
    <p>双击编辑待办事项</p>
</div>
</body>
</html>