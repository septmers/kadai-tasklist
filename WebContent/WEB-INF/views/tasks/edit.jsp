<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test = "${task != null}">
                <h2>ID：${task.id}のタスク内容編集</h2>
                    <form method="POST" action = "<c:url value = '/update' />">
                        <c:import url ="_form.jsp"/>
                    </form>

                <p><a href="<c:url value ='/index' />">一覧に戻る</a></p>
                <p><a href ="#" onclick="confirmDestroy();">このタスクを削除する</a></p>
                <form method ="POST" action="<c:url value = '/destroy' />">
                    <input type ="hidden"  name ="_token"  value="${_token}" />
                </form>
                <script>
                function confirmDestroy(){
                    if(confirm("本当に削除してよろしいでしょうか？")){
                        document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>