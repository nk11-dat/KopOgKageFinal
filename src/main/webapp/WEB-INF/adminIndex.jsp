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

        <h1 class="center-Text">${requestScope.insufficient_funds}</h1>

        <h3>Her kan du se alle ordere: </h3>

        <div style="padding:0px 0px 5px 5px">
            <label for="myInput">Søg: </label>
            <input type="text" id="myInput" onkeyup="searchByUsername()" placeholder="Søg efter kundenavn...">
        </div>
        <form method="post">
            <div class="table-responsive-xl">
                <table class="table-responsive-sm table" id="myTable">
                    <c:forEach var="userOrdersDTO" items="${requestScope.userOrdersDTOs}">
                        <tbody class="user"
                               id="user${userOrdersDTO.user.userId}">
                        <tr class="table-danger"> <%-- Loop over kunder --%>
                            <th scope="col" style="width: 44px">${userOrdersDTO.user.userId}</th>
                            <th scope="col">${userOrdersDTO.user.username}</th>
                            <th scope="col">Email: ${userOrdersDTO.user.email}</th>
                            <th scope="col">Saldo: ${userOrdersDTO.user.balance} kr</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <button type="button" class="btn" id="btnuserOrders${userOrdersDTO.user.userId}"
                                        onclick="showHideUserOrders('userOrders'+${userOrdersDTO.user.userId})">Show orders
                                        <%--Pak ind i if sættninger til forskelige pile--%>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                         class="bi bi-chevron-down" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd"
                                              d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"></path>
                                    </svg>
                                </button>
                            </th>
                        </tr>
                        </tbody>
                        <tbody class="userOrders${userOrdersDTO.user.userId}"
                               style="display: none">
                        <c:forEach var="orderDTO" items="${userOrdersDTO.orderOverviewHeaderAdminDTO}">
                            <tr class="order"><%--style="display: none"--%>
                                <th></th>
                                <th scope="col">Order Id: ${orderDTO.orderId}</th>
                                <th scope="col">${orderDTO.totalsum} kr</th>
                                <th scope="col">${orderDTO.date}</th>
                                <th scope="col">
                                    <c:if test="${orderDTO.status == true}">Betalt</c:if>
                                    <c:if test="${orderDTO.status == false}">Afventer betaling</c:if>
                                </th>
                                <th scope="col">
                                    <button
                                            <c:if test="${orderDTO.status == true}">disabled</c:if>
                                            <c:if test="${orderDTO.status == false}">formaction="OrderOverviewAdmin"
                                            name="orderid"
                                            value="${orderDTO.orderId}"</c:if>
                                            class="btn btn-secondary">
                                        Godkend
                                    </button>
                                    <button formaction="OrderOverviewAdmin2" formmethod="get" name="orderid"
                                            value="${orderDTO.orderId}" class="btn btn-secondary">
                                        Slet
                                    </button>
                                </th>
                            </tr>
                            <c:forEach var="orderItemDTO" items="${orderDTO.orderItem}">
                                <tr class="orderItems"><%--style="display: none"--%>
                                    <td></td>
                                    <td>${orderItemDTO.flavor}</td>
                                    <td>${orderItemDTO.quantity} stk</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </form>

        <%--Javascript til search funktion--%>
        <script>
            function searchByUsername() {
                var input, filter, table, tr, td, th, i, txtValue, tbody, currentClassId;
                input = document.getElementById("myInput"); //Få fat i input elementet
                filter = input.value.toUpperCase(); //hent det som blev skrevet i input elementet
                table = document.getElementById("myTable");
                tbody = table.getElementsByTagName("tbody");
                // tbodyHeader = tbody.getElementsByClassName("customer");


                for (i = 0; i < tbody.length; i++) { //Loop igennem <tbody>'s (kunder)
                    tr = tbody[i].getElementsByTagName("tr");
                    for (r = 0; r < tr.length; r++) { //Loop igennem hver <tr> i <tbody>'en
                        th = tr[0].getElementsByTagName("th")[1];
                        if (th) {
                            txtValue = th.textContent || th.innerText; //fyld teksen fra <th> i txtValue
                            if (txtValue.toUpperCase().indexOf(filter) > -1) { //sammenlign textValue med fliter
                                currentClassId = tbody[i].classList[1]; //gem ClassId hvis det matcher med søgning
                            }
                        }
                        if (tbody[i].classList.contains(currentClassId)) {
                            tr[r].style.display = ""; //vis <tr> hvis ClassId det matcher med søgning
                        } else {
                            tr[r].style.display = "none"; //ellers skjul <tr>
                        }
                    }
                }
            }

            function showHideUserOrders(identifier) {
                var table, btn, i, tbody;
                table = document.getElementById("myTable");
                tbody = table.getElementsByTagName("tbody");


                for (i = 0; i < tbody.length; i++) { //Loop igennem <tbody>'s (kunder)
                    if (tbody[i].classList.contains(identifier)) {
                        btn = document.getElementById("btn"+identifier);
                        if (tbody[i].style.display == "none"){
                            tbody[i].style.display = ""; //vis <tbody> hvis identifier eller det en customer<tbody>
                            btn.innerHTML = "Hide orders ";
                        //(Scary looking) Viser pil ned
                            btn.innerHTML += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-up" viewBox="0 0 16 16"> <path fill-rule="evenodd" d="M7.646 4.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1-.708.708L8 5.707l-5.646 5.647a.5.5 0 0 1-.708-.708l6-6z"/></svg>';
                        }
                        else
                        {
                            tbody[i].style.display = "none";
                            btn.innerHTML = "Show orders";
                            btn.innerHTML += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-down" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"></path></svg>';
                            //(Scary looking) Viser pil up

                        }
                    }
                }
            }
        </script>
    </jsp:body>

</t:pagetemplate>