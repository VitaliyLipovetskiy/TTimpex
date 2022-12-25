<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/timestamp.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <h3 class="text-center"><spring:message code="app.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/cardOld/--%>
        <div class="cardOld border-dark" hidden>
            <div class="cardOld-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-2">
                            <label for="startDate"><spring:message code="common.startDate"/></label>
                            <input class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate"><spring:message code="common.endDate"/></label>
                            <input class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
<%--                        <div class="offset-2 col-3">--%>
<%--                            <label for="startTime"><spring:message code="meal.startTime"/></label>--%>
<%--                            <input class="form-control" name="startTime" id="startTime" autocomplete="off">--%>
<%--                        </div>--%>
<%--                        <div class="col-3">--%>
<%--                            <label for="endTime"><spring:message code="meal.endTime"/></label>--%>
<%--                            <input class="form-control" name="endTime" id="endTime" autocomplete="off">--%>
<%--                        </div>--%>
                    </div>
                </form>
            </div>
            <div class="cardOld-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="ctx.updateTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="common.filter"/>
                </button>
            </div>
        </div>
        <br/>

<%--        <jsp:include page="table-scroll.jsp"/>--%>

        <table class="table table-striped table-bordered table-hover table-sm" id="datatable" >
            <thead>
            <tr class="text-center">
                <th rowspan="2" class="align-middle">№</th>
                <th rowspan="2" class="align-middle"><spring:message code="employee.name"/></th>
                <th rowspan="2" class="align-middle">
                    <div class='align-middle text-center cell-choice'>
                        <input type='checkbox' id="filtered" onclick="selectFilter($(this));"/>
                    </div>
                </th>
                <th id="month"></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">
                    <input type="hidden" id="date" name="date">

                    <div class="container-fluid">
                        <div class="form-group row col-sm-12">
                            <input type="text" readonly class="form-control-plaintext form-control-lg" id="title" name="title">
                        </div>
                        <div class="form-group row">
                            <label for="comingCorrectTime" class="col-sm-4 col-form-label"><spring:message code="employee.comingCorrectTime"/></label>
                            <div class="col-sm-4">
                                <input type="time" class="form-control" id="comingCorrectTime" name="comingCorrectTime">
                            </div>
                            <div class="col-sm-4">
                                <input type="time" class="form-control" id="comingAutoTime" name="comingAutoTime" disabled>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="leavingCorrectTime" class="col-sm-4 col-form-label"><spring:message code="employee.leavingCorrectTime"/></label>
                            <div class="col-sm-4">
                                <input type="time" class="form-control" id="leavingCorrectTime" name="leavingCorrectTime">
                            </div>
                            <div class="col-sm-4">
                                <input type="time" class="form-control" id="leavingAutoTime" name="leavingAutoTime" disabled>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="penalty" class="col-sm-4 col-form-label"><spring:message code="employee.penalty"/></label>
                            <div class="col-sm-8">
                                <input type="number" class="form-control" id="penalty" name="penalty">
                            </div>
                        </div>
                        <div class="form-group row worked-out">
                            <label for="workedOut" class="col-sm-4 col-form-label"><spring:message code="employee.workedOut"/></label>
                            <div class="col-sm-8">
                                <input type="number" class="form-control" id="workedOut" name="workedOut">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-sm-4 offset-sm-4" >
                                <input type="checkbox" class="form-check-input" id="dayOff" name="dayOff" disabled>
                                <label for="dayOff" class="form-check-label"><spring:message code="employee.dayOff"/></label>
                            </div>
                            <div class="form-group col-sm-4">
                                <input type="checkbox" class="form-check-input" id="worked" name="worked" disabled>
                                <label for="worked" class="form-check-label"><spring:message code="employee.worked"/></label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>


<br>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="timestamp"/>
</jsp:include>
</html>
