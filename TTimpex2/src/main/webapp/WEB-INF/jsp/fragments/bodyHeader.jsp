<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <div class="container">
        <a href="report" class="navbar-brand"><img src="resources/images/icon-report.png">
        <spring:message code="app.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
           <ul class="navbar-nav ml-auto">
                <li class="nav-item">
<%--                    <sec:authorize access="isAuthenticated()">--%>
                        <form:form class="form-inline my-2" action="logout" method="post">
<%--                            <sec:authorize access="hasRole('ADMIN')">--%>
                            <a class="btn btn-info mr-1" href="employees/daysoff">
                                <spring:message code="employee.daysOff"/>
                            </a>
                            <a class="btn btn-info mr-1" href="employees">
                                <spring:message code="employee.title"/>
                            </a>
                            <a class="btn btn-info mr-1" href="scodes">
                                <spring:message code="scode.title"/>
                            </a>
                            <a class="btn btn-info mr-1" href="users">
                                <spring:message code="user.title"/>
                            </a>
<%--                            </sec:authorize>--%>
<%--                            <a class="btn btn-info mr-1" href="profile">--%>
<%--&lt;%&ndash;                                <sec:authentication property="principal.userTo.name"/> &ndash;%&gt;--%>
<%--                                <spring:message code="app.profile"/>--%>
<%--                            </a>--%>
                            <button class="btn btn-primary my-1" type="submit">
                                <span class="fa fa-sign-out"></span>
                            </button>
                        </form:form>
<%--                    </sec:authorize>--%>
<%--                    <sec:authorize access="isAnonymous()">--%>
<%--                        <form:form class="form-inline my-2" id="login_form" action="spring_security_check"--%>
<%--                                   method="post">--%>
<%--                            <input class="form-control mr-1" type="text" placeholder="Email" name="username">--%>
<%--                            <input class="form-control mr-1" type="password" placeholder="Password" name="password">--%>
<%--                            <button class="btn btn-success" type="submit">--%>
<%--                                <span class="fa fa-sign-in"></span>--%>
<%--                            </button>--%>
<%--                        </form:form>--%>
<%--                    </sec:authorize>--%>
                </li>
                <li class="nav-item dropdown">

                </li>
            </ul>
        </div>
    </div>
</nav>

