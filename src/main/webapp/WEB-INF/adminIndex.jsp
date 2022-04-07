<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Admin
    </jsp:attribute>

    <jsp:attribute name="footer">
            Admin
    </jsp:attribute>

    <jsp:body>

        <h3>Her kan du se alle ordere: </h3>

        <form method="post">
            <table class="table">
                <c:forEach var="orderDTO" items="${requestScope.orderDTOList}">
                    <thead class="table-danger">
                    <tr>
                        <th scope="col">${orderDTO.orderId}</th>
                        <th scope="col">${orderDTO.costumerUsername}</th>
                        <th scope="col">${orderDTO.totalsum} kr</th>
                        <th scope="col">${orderDTO.date}</th>
                        <th scope="col">${orderDTO.status}</th>
                        <th scope="col">
                                <button <c:if test="${orderDTO.status == true}">disabled</c:if>
                                        <c:if test="${orderDTO.status == false}">formaction="OrderOverview" name="orderid" value="${orderDTO.orderId}"</c:if>
                                        class="btn btn-secondary">
                                    Godkend
                                </button>
                            </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderItemDTO" items="${orderDTO.orderItem}">
                        <tr>
                            <th scope="row"></th>
                            <td>${orderItemDTO.flavor}</td>
                            <td>${orderItemDTO.quantity} stk</td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:forEach>
            </table>
                <%--<table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">#orderID</th>
                        <th scope="col">Brugernavn</th>
                        <th scope="col">Cupcaketype</th>
                        <th scope="col">Antal</th>
                        <th scope="col">Total pris</th>
                        <th scope="col">Bestillings Dato</th>
                        <th scope="col">Status</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderDTO" items="${requestScope.orderDTOList}">
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.costumerUsername}</td>
                            <td>${orderDTO.flavor}</td>
                            <td>${orderDTO.quantity}</td>
                            <td>${orderDTO.totalsum}</td>
                            <td>${orderDTO.date}</td>
                            <td>${orderDTO.status}</td>
                            <td>
                                <c:if test="${orderDTO.status == true}">
                                    <button disabled class="btn btn-secondary">Godkendt</button>
                                </c:if>
                                <c:if test="${orderDTO.status == false}">
                                    <button formaction="OrderOverview" name="orderid" value="${orderDTO.orderId}" class="btn btn-secondary">Afventer</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>--%>
        </form>

    </jsp:body>

</t:pagetemplate>