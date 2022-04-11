<
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Ordreoversigt

    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage, det her er en footer.
    </jsp:attribute>

    <jsp:body>

        <form action="Thanks" method="get">
        <table class="table">
            <thead class="table-danger">
            <tr>
                <th scope="col"></th>
                <th scope="col">Bund</th>
                <th scope="col">Top</th>
                <th scope="col">Antal</th>
                <th scope="col">Pris</th>
                <th scope="col"></th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <c:forEach var="orderItemDTOT" items="${requestScope.orderItemDTOList}">
            <tr>
                <th scope="row"></th>
                <td>${orderItemDTOT.bottom}</td>
                <td>${orderItemDTOT.topping}</td>
                <td>${orderItemDTOT.quantity} stk</td>
                <td>${orderItemDTOT.price}</td>

            </tr>
            </c:forEach>
            </tr>

            </tbody>
        </table>
            <h4>Total pris: ${requestScope.totalPrice}</h4>

            <button type="submit" class="btn btn-danger" style="background-color: lightpink">
                Bekræft ordre
            </button>

            </form>

    </jsp:body>

</t:pagetemplate>
