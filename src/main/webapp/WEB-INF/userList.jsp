<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Admin kundeliste
    </jsp:attribute>

    <jsp:attribute name="footer">
            Admin kundeliste
    </jsp:attribute>

    <jsp:body>

        <h3>Her kan du se alle kunder: </h3>

        <form method="get">
            <table class="table">
                <c:forEach var="user" items="${requestScope.userList}">
                    <thead class="table-danger">
                    <tr>
                        <th scope="col">${user.userId}</th>
                        <th scope="col">${user.roleId}</th>
                        <th scope="col">${user.username}</th>
                        <th scope="col">${user.password}</th>
                        <th scope="col">${user.email}</th>
                        <th scope="col">${user.balance}</th>
                        <th scope="col">
                </c:forEach>
            </table>
        </form>

    </jsp:body>

</t:pagetemplate>