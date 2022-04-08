<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>


<t:pagetemplate>

    <jsp:attribute name="header">
        Bestilling
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage, det her er en footer
    </jsp:attribute>

    <jsp:body>


        <form action="/CupcakeOrder" method="post">


            <table class="table">
                <div class="dropdown">
                    Topping
                    <select class="form-select" aria-label="Default select example">
                        <option selected>Her vælger du din topping</option>
                        <c:forEach var="cupcakeDTO" items="${applicationScope.cupcakeToppingList}">

                            <option value="1">${cupcakeDTO.flavor} (${cupcakeDTO.price} kr.)</option>

                        </c:forEach>
                    </select>

                </div>

                <table class="table">
                    <div class="dropdown">
                        Bund
                        <select class="form-select" aria-label="Default select example">
                            <option selected>Her vælger du din bund</option>
                            <c:forEach var="cupcakeDTO" items="${applicationScope.cupcakeBottomList}">

                                <option value="1">${cupcakeDTO.flavor} (${cupcakeDTO.price} kr.)</option>

                            </c:forEach>
                        </select>

                    </div>
                </table>

                    <input class="form-control" min="0" max="8" type="number" id="quantity" name="quantity"><br>
                    <label for="quantity">Vælg antal Cupcakes</label><br><br>



                <button type="button" class="btn btn-primary" formaction="/OrderOverviewUser">Bekræft</button>
                 </table>
        </form>


    </jsp:body>
</t:pagetemplate>
