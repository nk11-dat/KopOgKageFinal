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

        <h1 class="center">${requestScope.insufficient_funds}</h1>

        <h3>Her kan du se alle ordere: </h3>

        <div style="padding:0px 0px 5px 5px">
            <label for="myInput">Søg: </label>
            <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Søg efter kundenavn...">
        </div>
        <form method="post">
            <table class="table" id="myTable">
                <c:forEach var="orderDTO" items="${requestScope.orderDTOList}">
                    <tbody>
                    <tr class="table-danger">
                        <th scope="col">${orderDTO.orderId}</th>
                        <th scope="col" class="customerName">${orderDTO.costumerUsername}</th>
                        <th scope="col">${orderDTO.totalsum} kr</th>
                        <th scope="col">${orderDTO.date}</th>
                        <th scope="col">
                            <c:if test="${orderDTO.status == true}">Betalt</c:if>
                            <c:if test="${orderDTO.status == false}">Afventer betaling</c:if>
                        </th>
                        <th scope="col">
                            <button
                                    <c:if test="${orderDTO.status == true}">disabled</c:if>
                                    <c:if test="${orderDTO.status == false}">formaction="OrderOverviewAdmin" name="orderid"
                                    value="${orderDTO.orderId}"</c:if>
                                    class="btn btn-secondary">
                                Godkend
                            </button>
                            <button formaction="OrderOverviewAdmin2" formmethod="get" name="orderid" value="${orderDTO.orderId}" class="btn btn-secondary">
                                Slet
                            </button>
                        </th>
                    </tr>
                    <c:forEach var="orderItemDTO" items="${orderDTO.orderItem}">
                        <tr>
                            <td></td>
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

        <%--Javascript til search funktion--%>
        <script>
            function myFunction() {
                var input, filter, table, tr, td, th, i, txtValue, tbody;
                input = document.getElementById("myInput"); //Få fat i input elementet
                filter = input.value.toUpperCase(); //hent det som blev skrevet i input elementet
                table = document.getElementById("myTable");
                tbody = table.getElementsByTagName("tbody");

                for (i = 0; i < tbody.length; i++) { //Loop igennem <tbody>'s (ordere)
                    tr = tbody[i].getElementsByTagName("tr");
                    for (r = 0; r < tr.length; r++) { //Loop igennem hver <tr> i <tbody>en
                        th = tr[0].getElementsByTagName("th")[1];
                        if (th) {
                            txtValue = th.textContent || th.innerText; //fyld teksen fra <th> i txtValue
                            if (txtValue.toUpperCase().indexOf(filter) > -1) { //sammenlign textValue med fliter
                                tr[r].style.display = ""; //gør intet hvis det matcher med søgning
                            } else {
                                tr[r].style.display = "none"; //ellers skjul rækken
                            }
                        }
                    }
                }
            }
        </script>
    </jsp:body>

</t:pagetemplate>