<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">
            Opret bruger
    </jsp:attribute>

    <jsp:body>


        <!-- Section: Design Block -->
        <section class="text-center">
            <!-- Background image -->
                <div class="card-body py-5 px-md-5">

                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-8">
                            <h2 class="fw-bold mb-5">Opret</h2>

                            <form action="CreateUser" method="post">
                                <!-- name input -->
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-6 mb-4">
                                        <div class="form-outline">
                                            <input type="text" id="navn" name="username" class="form-control" />
                                            <label class="form-label" for="navn">navn</label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Email input -->
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-6 mb-4">
                                        <input type="email" id="email" name="email" class="form-control" />
                                        <label class="form-label" for="email">Email adresse</label>
                                    </div>
                                </div>

                                <!-- Password input -->
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-6 mb-4">
                                        <input type="password" id="password1" name="password1" class="form-control" />
                                        <label class="form-label" for="password1">Password</label>
                                    </div>
                                </div>

                                <!-- Check password input -->
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-6 mb-4">
                                        <input type="password" id="password2" name="password2" class="form-control" />
                                        <label class="form-label" for="password2">Gentag Password</label>
                                    </div>
                                </div>


                                <!-- Balance input -->
                                <div class="row d-flex justify-content-center">
                                    <div class="col-md-6 mb-4">
                                        <input type="number" id="balance" name="saldo" class="form-control" />
                                        <label class="form-label" for="balance">Saldo</label>
                                    </div>
                                </div>

                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4">
                                    Opret
                                </button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>


    </jsp:body>
</t:pagetemplate>