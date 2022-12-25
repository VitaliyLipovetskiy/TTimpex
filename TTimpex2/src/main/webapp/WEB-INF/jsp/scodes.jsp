<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/scodes.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="scode.title"/></h3>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="scode.add"/>
        </button>
        <table class="table table-striped table-bordered table-hover table-sm" id="datatable">
            <thead>
            <tr class="text-center">
                <th class="align-middle text-center"><spring:message code="scode.card"/></th>
                <th class="align-middle text-center"><spring:message code="scode.scode"/></th>
                <th class="align-middle text-center"><spring:message code="employee.name"/></th>
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
                    <input type="hidden" id="method" name="method">

                    <div class="container">
                        <div class="row ">
                            <div class="form-group col-sm">
                                <label for="card" class="col-form-label"><spring:message code="scode.card"/></label>
                                <input type="text" class="form-control" id="card" name="card"
                                       placeholder="<spring:message code="scode.card"/>">
                            </div>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row ">
                            <div class="form-group col-sm">
                                <label for="scode" class="col-form-label"><spring:message code="scode.scode"/></label>
                                <input type="text" class="form-control" id="scode" name="scode"
                                       placeholder="<spring:message code="scode.scode"/>">
                            </div>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row ">
                            <div class="form-group col-sm">
                                <label for="employee" class="col-form-label"><spring:message code="employee.name"/></label>
                                <div class="input-group">
                                    <input readonly type="text" class="form-control clearable" id="employee" name="employee"
                                           placeholder="<spring:message code="employee.name"/>">
                                    <button type="button" class="btn bg-transparent" style="margin-left: -40px; z-index: 100;" onclick="clearEmployee()">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
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
    <jsp:param name="page" value="scode"/>
</jsp:include>
</html>

