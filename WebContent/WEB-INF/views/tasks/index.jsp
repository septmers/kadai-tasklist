<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app/jsp">
    <c:param name = "content">
        <div id = "pagination">
            <c:forEach var="i"  begin="1"  end="${((task_count - 1) / 15) + 1}">
                <c:when test = ${page == i}>
                    <c:out value = "${i}"/>&nbsp;
                </c:when>
                <c:otherwise>
                    <a href ="<c:url value = '/index?page=${i}' />"><c:out value = "${i}"/></a>&nbsp;
                </c:otherwise>
            </c:forEach>
        </div>
        <c:if test="${flush != null}">
            <div id = "flush_success">
                <c:out value ="${flush}" />
            </div>
        </c:if>
        <h2>タスク一覧</h2>
        <ul>
            <c:forEach var="task" items="${tasks}">
                <li>
                    <a href = "<c:url value ='/show?id=${task.id}' />"><c:out value ="${task.id}" /></a>
                    :<c:out value ="${task.content}" />
                </li>
            </c:forEach>
        </ul>

        <p><a href = "<c:url value = '/new' />">新規タスクの追加</a></p>

    </c:param>
</c:import>