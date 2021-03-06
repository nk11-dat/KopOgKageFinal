<
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage, det her er en footer.
    </jsp:attribute>

    <jsp:body>
        <h4>Du har lagt følgende varer i kurv: </h4>

        <form action="ConfirmOrder" method="post">
        <table class="table" style="width: 70%">
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
            <tr class="rowid${requestScope.orderItemDTOList.indexOf(orderItemDTOT)}">
                <th scope="row"></th>
                <td>${orderItemDTOT.bottom}</td>
                <td>${orderItemDTOT.topping}</td>
                <td>${orderItemDTOT.quantity} stk</td>
                <td>${orderItemDTOT.price}</td>
                <td>
                    <button name="rowIndex" type="submit" style="background-color: lightpink" class=" btn btn-danger text-dark" formaction="Cart" formmethod="post" value="${requestScope.orderItemDTOList.indexOf(orderItemDTOT)}">
                    Fjern
                    </button>
                </td>
            </tr>
            </c:forEach>
            </tr>

            </tbody>
        </table>
            <h4>Total pris: ${requestScope.totalPrice}</h4>

            <button type="submit"  style="background-color: lightpink" class=" btn btn-danger text-dark">
                Bekræft ordre
            </button>

            </form>

    </jsp:body>

</t:pagetemplate>
