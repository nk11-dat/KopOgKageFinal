<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>


<t:pagetemplate>

    <jsp:attribute name="header">
        Bestilling
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage, det her er en footer
    </jsp:attribute>

    <jsp:body>


        <form method="post">
            <table class="table">
                <div class="dropdown">
                    Topping
                    <select class="form-select" aria-label="Default select example">
                        <option selected>Open this select menu</option>
                        <c:forEach var="cupcakeTopping" items="${applicationScope.cupcakeToppingList}">
                            <option value="${cupcakeTopping.toppingId}">${cupcakeTopping.flavor} (${cupcakeTopping.price} kr.)</option>
                        </c:forEach>
                    </select>
                </div>
            </table>

            <table class="table">
                <div class="dropdown">
                    Bund
                    <select class="form-select" aria-label="Default select example">
                        <option selected>Open this select menu</option>
                        <c:forEach var="cupcakeBottom" items="${applicationScope.cupcakeBottomList}">
                            <option value="${cupcakeBottom.bottomId}">${cupcakeBottom.flavor} (${cupcakeBottom.price} kr.)</option>
                        </c:forEach>
                    </select>
                </div>
            </table>
        </form>


    </jsp:body>
</t:pagetemplate>
