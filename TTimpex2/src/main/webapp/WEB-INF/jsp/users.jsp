<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<%--<script type="text/javascript" src="resources/js/common.js" defer></script>--%>
<script type="text/javascript" src="resources/js/users.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="user.title"/></h3>
        <%--        <button class="btn btn-primary" onclick="add()">--%>
        <%--            <span class="fa fa-plus"></span>--%>
        <%--            <spring:message code="common.add"/>--%>
        <%--        </button>--%>
        <%--        <table class="table table-striped table-bordered table-hover table-sm" id="datatable">--%>
        <%--            <thead>--%>
        <%--            <tr class="text-center">--%>
        <%--                <th class="align-middle text-center"><spring:message code="employee.name"/></th>--%>
        <%--                <th class="align-middle text-center"><spring:message code="scode.card"/></th>--%>
        <%--                <th class="align-middle text-center"><spring:message code="employee.department"/></th>--%>
        <%--                <th class="align-middle text-center" style="width: 50px"><spring:message code="employee.startTime"/></th>--%>
        <%--                <th class="align-middle text-center" style="width: 50px"><spring:message code="employee.endTime"/></th>--%>
        <%--                <th class="align-middle"></th>--%>
        <%--                <th>--%>
        <%--                    <div class='align-middle text-center cell-choice'>--%>
        <%--                        <input type='checkbox' checked id="filtered" onclick='selectFilter($(this));'/>--%>
        <%--                    </div>--%>
        <%--                </th>--%>
        <%--            </tr>--%>
        <%--            </thead>--%>
        <%--        </table>--%>
    </div>
</div>

<br>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

