<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <title><spring:message code="app.title"/></title>
  <base href="${pageContext.request.contextPath}/"/>

  <link rel="stylesheet" href="webjars/bootstrap/4.6.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="webjars/datatables/1.11.4/css/dataTables.bootstrap4.min.css">
<%--  <link rel="stylesheet" href="webjars/datatables/1.11.4/css/jquery.dataTables.min.css">--%>
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedcolumns/4.1.0/css/fixedColumns.dataTables.min.css">
  <link rel="stylesheet" href="webjars/noty/3.1.4/lib/noty.css"/>
<%--  <link rel="stylesheet" href="webjars/datetimepicker/2.5.20-1/jquery.datetimepicker.css">--%>

  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/fixedheader/3.2.4/css/fixedHeader.dataTables.min.css">

  <link rel="stylesheet" href="resources/css/style.css?v=2">

<%--http://stackoverflow.com/a/24070373/548473--%>
  <script src="webjars/jquery/3.6.0/jquery.min.js"></script>
  <script src="webjars/bootstrap/4.6.1/js/bootstrap.min.js" defer></script>
  <script src="webjars/datatables/1.11.4/js/jquery.dataTables.min.js" defer></script>
  <script src="webjars/datatables/1.11.4/js/dataTables.bootstrap4.min.js" defer></script>
  <script src="webjars/noty/3.1.4/lib/noty.min.js" defer></script>
<%--  <script src="webjars/datetimepicker/2.5.20-1/build/jquery.datetimepicker.full.min.js" defer></script>--%>
  <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/fixedcolumns/4.1.0/js/dataTables.fixedColumns.min.js" defer></script>

<%--  <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.5.1.js" defer></script>--%>
  <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/fixedheader/3.2.4/js/dataTables.fixedHeader.min.js" defer></script>

</head>

