<<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

        <h4>Tak for din bestilling hos Olsker Cupcakes.</h4>
        <h5>Dette er en ordrebekræfetelse på din ordre.</h5>
        <br>

        <table class="table table-bordered" style="width: 70%">
            <thead>
            <tr>
                <th colspan="2">Ordreinformation</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Navn:</td>
                <td>
                        ${requestScope.orderInformationDTO.name}
                </td>
            </tr>
            <tr>
                <td>Ordrenummer:</td>
                <td>${requestScope.orderInformationDTO.orderId} </td>
            </tr>
            <tr>
                <td>Ordredato:</td>
                <td>${requestScope.orderInformationDTO.date}</td>
            </tr>
            <tr>
                <td>Betaling:</td>
                <td>Ved afhæntning i butikken </td>
            </tr>
            </tbody>
        </table>

        <br>

        <table class="table table-bordered" style="width: 70%">
            <thead>
            <tr>
                <th colspan="3">Bestilte varer</th>
            </tr>

            <tr>
                <th scope="col">Cupcake</th>
                <th scope="col">Antal</th>
                <th scope="col">Pris</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="orderitemDTOList" items="${requestScope.orderItemDTOList}">
                <tr>
                    <td>${orderitemDTOList.bottom} - ${orderitemDTOList.topping}</td>
                    <td>${orderitemDTOList.quantity} stk</td>
                    <td>${orderitemDTOList.price} kr</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="2"></td>
                <td style="font-weight: bold">Total pris: ${requestScope.totalSum} kr </td>
            </tr>
            </tfoot>
        </table>
        <br>
        <br>

    </jsp:body>

</t:pagetemplate>
