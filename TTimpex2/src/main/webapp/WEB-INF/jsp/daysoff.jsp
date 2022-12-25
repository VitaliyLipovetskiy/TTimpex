<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/daysoff.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <h3 class="text-center"><spring:message code="employee.daysOff"/></h3>

        <div class="cardOld border-dark">
            <div class="cardOld-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-4">
                            <label for="filterMonth"><spring:message code="daysoff.month"/></label>
                            <input class="form-control" name="filterMonth" id="filterMonth" type="month" autocomplete="off" onchange="updateTableByData()">
                        </div>
                    </div>
                </form>
            </div>
<%--            <div class="cardOld-footer text-right">--%>
<%--                <button class="btn btn-danger" onclick="clearFilter()">--%>
<%--                    <span class="fa fa-remove"></span>--%>
<%--                    <spring:message code="common.cancel"/>--%>
<%--                </button>--%>
<%--                <button class="btn btn-primary" onclick="ctx.updateTable()">--%>
<%--                    <span class="fa fa-filter"></span>--%>
<%--                    <spring:message code="common.filter"/>--%>
<%--                </button>--%>
<%--            </div>--%>
        </div>
        <br>

<%--        <button class="btn btn-primary" onclick="add()">--%>
<%--            <span class="fa fa-plus"></span>--%>
<%--            <spring:message code="common.add"/>--%>
<%--        </button>--%>
        <table class="table table-striped table-bordered table-sm" id="datatable">
            <thead>
            <tr id="days" class="text-center">
<%--                <th class="align-middle text-center"></th>--%>
                <th class="align-middle text-center"><spring:message code="employee.name"/></th>
                <th class="align-middle text-center"></th>
<%--                <th class="align-middle text-center"><spring:message code="employee.department"/></th>--%>
                <th class="align-middle">
                    <div class="align-middle text-center cell-choice">
                        <input type="checkbox" id="filtered" onClick="selectFilter($(this));"/>
                    </div>
<%--                    <div class='align-middle text-center cell-choice'>--%>
<%--                        <input type='checkbox' id="filtered" onclick="selectFilter($(this));"/>--%>
<%--                    </div>--%>
                </th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<br>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
