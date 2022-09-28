<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/employees.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <h3 class="text-center"><spring:message code="employee.title"/></h3>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="employee.add"/>
        </button>
        <table class="table table-striped table-bordered table-hover table-sm" id="datatable">
            <thead>
            <tr class="text-center">
                <th class="align-middle text-center"><spring:message code="employee.name"/></th>
                <th class="align-middle text-center"><spring:message code="employee.cardOld"/></th>
                <th class="align-middle text-center"><spring:message code="employee.department"/></th>
                <th class="align-middle text-center" style="width: 50px"><spring:message code="employee.startTime"/></th>
                <th class="align-middle text-center" style="width: 50px"><spring:message code="employee.endTime"/></th>
                <th class="align-middle text-center"><spring:message code="employee.timeTracking"/></th>
                <th class="align-middle text-center"><spring:message code="employee.recruitment"/></th>
                <th class="align-middle text-center"><spring:message code="employee.dismissal"/></th>
                <th>
                    <div class="align-middle text-center cell-choice">
                        <input type='checkbox' id="filtered" onclick='selectFilter($(this));'/>
                    </div>
                </th>
                <th class="align-middle text-center"></th>
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

                    <div class="container">
                        <div class="row ">
                            <div class="form-group col-sm">
                                <label for="lastName" class="col-form-label"><spring:message code="employee.lastName"/></label>
                                <input type="text" class="form-control" id="lastName" name="lastName"
                                       placeholder="<spring:message code="employee.lastName"/>">
                            </div>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row justify-content-around">
                            <div class="form-group col-5">
                                <label for="firstName" class="col-form-label"><spring:message code="employee.firstName"/></label>
                                <input type="text" class="form-control" id="firstName" name="firstName"
                                       placeholder="<spring:message code="employee.firstName"/>">
                            </div>

                            <div class="form-group col-7">
                                <label for="middleName" class="col-form-label"><spring:message code="employee.middleName"/></label>
                                <input type="text" class="form-control" id="middleName" name="middleName"
                                       placeholder="<spring:message code="employee.middleName"/>">
                            </div>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row">
                            <div class="form-group col-sm-8">
                                <label for="department" class="col-form-label"><spring:message code="employee.department"/></label>
                                <select class="custom-select mr-sm-2" id="department">
                                    <option selected value="0"></option>
                                </select>
                            </div>

                            <div class="form-group col-sm-4">
                                <label for="cardOld" class="col-form-label"><spring:message code="employee.cardOld"/></label>
                                <input type="email" class="form-control" id="cardOld" name="cardId"
                                       placeholder="<spring:message code="employee.cardOld"/>">
                            </div>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row justify-content-around">
                            <div class="form-group col-sm-5">
                                <label for="startTime" class="col-form-label"><spring:message code="employee.startTime"/></label>
                                <input type="time" class="form-control" id="startTime" name="startTime"
                                       placeholder="<spring:message code="employee.startTime"/>">
                            </div>

                            <div class="form-group col-sm-5">
                                <label for="endTime" class="col-form-label"><spring:message code="employee.endTime"/></label>
                                <input type="time" class="form-control" id="endTime" name="endTime"
                                       placeholder="<spring:message code="employee.endTime"/>">
                            </div>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row justify-content-around">
                            <div class="form-group col-sm-5">
                                <label for="recruitment" class="col-form-label"><spring:message code="employee.recruitment"/></label>
                                <input type="date" class="form-control" id="recruitment" name="recruitment"
                                       placeholder="<spring:message code="employee.recruitment"/>">
                            </div>

                            <div class="form-group col-sm-5">
                                <label for="dismissal" class="col-form-label"><spring:message code="employee.dismissal"/></label>
                                <input type="date" class="form-control" id="dismissal" name="dismissal"
                                       placeholder="<spring:message code="employee.dismissal"/>">
                            </div>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-4 offset-md-1">
                                <input type="checkbox" class="form-check-input" id="accountingForHoursWorked" name="accountingForHoursWorked" onclick="changeAccountingForHoursWorked()">
                                <label for="accountingForHoursWorked" class="form-check-label"><spring:message code="employee.timeTracking"/></label>
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
    <jsp:param name="page" value="employee"/>
</jsp:include>
</html>
