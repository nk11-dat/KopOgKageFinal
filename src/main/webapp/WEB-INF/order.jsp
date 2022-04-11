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
                    <select name="topping" class="form-select " aria-label="Default select example">
                        <option selected>Her vælger du din topping</option>
                        <c:forEach var="cupcakeDTO" items="${applicationScope.cupcakeToppingList}">

                            <option value="${cupcakeDTO.toppingId}">${cupcakeDTO.flavor} (${cupcakeDTO.price} kr.)</option>

                        </c:forEach>
                    </select>

                </div>

                <table class="table">
                    <div class="dropdown">
                        Bund
                        <select name="bottom" class="form-select" aria-label="Default select example">
                            <option selected>Her vælger du din bund</option>
                            <c:forEach var="cupcakeDTO" items="${applicationScope.cupcakeBottomList}">

                                <option value="${cupcakeDTO.bottomId}">${cupcakeDTO.flavor} (${cupcakeDTO.price} kr.)</option>

                            </c:forEach>
                        </select>

                    </div>
                </table>
                <br>


                <div class="input-group  ">
                    <button onclick="numberFunction('-') " style="min-width: 2.5rem" class="btn btn-decrement btn-outline-secondary btn-minus" type="button"><strong>−</strong></button>
                    <input  name="quantity" id="number" type="number" inputmode="decimal" style="text-align: center" class="form-control " placeholder="0">
                    <button onclick="numberFunction('+') " style="min-width: 2.5rem" class="btn btn-increment btn-outline-secondary btn-plus" type="button"><strong>+</strong></button>
                </div>
                <br><br>



                <button type="submit" class="btn btn-primary" value="submit" formaction="CupcakeOrder" formmethod="post" >Læg i kurv</button>
                 </table>
        </form>

        <script>
            function numberFunction(type)
            {
                var input;
                input = document.getElementById("number");
                if (type == '+')
                {
                    input.value++;
                }else if (input.value >= 1) {
                    input.value--;
                }
            }
        </script>
    </jsp:body>
</t:pagetemplate>
