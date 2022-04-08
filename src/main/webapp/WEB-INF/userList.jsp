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
                    <thead class="table-danger">
                    <tr>
                        <th scope="col">Bruger ID</th>
                        <!--   <th scope="col">Role</th>-->
                        <th scope="col">Navn</th>
                        <th scope="col">Kodeord</th>
                        <th scope="col">Email</th>
                        <th scope="col">Saldo</th>
                        <th scope="col">
                        </th>
                    </tr>
                    </thead>
                <tbody>
                    <c:forEach var="user" items="${requestScope.userList}">
                    <tr>
                        <td scope="col">${user.userId}</td>
                        <!--    <c:if test="${user.roleId == 1}">
                        <td> Bruger </td>
                        </c:if> -->
                        <td scope="col">${user.username}</td>
                        <td scope="col">${user.password}</td>
                        <td scope="col">${user.email}</td>
                        <td scope="col">${user.balance}</td>
                        <td scope="col">
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>

    </jsp:body>

</t:pagetemplate>