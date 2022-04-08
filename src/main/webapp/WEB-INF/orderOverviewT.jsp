<<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

        <form method="get">



        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Bund</th>
                <th scope="col">Top</th>
                <th scope="col">Antal</th>
                <th scope="col">Pris</th>
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
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </c:forEach>
            </tr>

            </tbody>
        </table>
            <h4>Total pris: ${requestScope.totalPrice}</h4>

        <button type="button" class="btn btn-primary" confirm="Are your sure?">Bekræft</button>

        </form>
    </jsp:body>

</t:pagetemplate>
