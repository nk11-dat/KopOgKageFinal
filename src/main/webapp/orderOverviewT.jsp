<<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Ordreoversigt
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage, det her er en footer.
    </jsp:attribute>

    <jsp:body>

        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Bund</th>
                <th scope="col">Top</th>
                <th scope="col">Antal</th>
                <th scope="col">Pris</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">1</th>
                <td>Vanilia</td>
                <td>Blåbær</td>
                <td>3</td>
                <td>45</td>
            </tr>
            <tr>
                <th scope="row">2</th>
                <td>Vanilia</td>
                <td>Blåbær</td>
                <td>3</td>
                <td>45</td>
            </tr>
            <tr>
                <th scope="row">3</th>
                <td>Vanilia</td>
                <td>Blåbær</td>
                <td>3</td>
                <td>45</td>
            </tr>
            <tr>
                <th scope="row"></th>
                <td></td>
                <td></td>
                <td></td>
                <td>Total sum: </td>
            </tr>
            </tbody>
        </table>

        <button type="button" class="btn btn-primary" confirm="Are your sure?">Bekræft</button>

    </jsp:body>

</t:pagetemplate>
