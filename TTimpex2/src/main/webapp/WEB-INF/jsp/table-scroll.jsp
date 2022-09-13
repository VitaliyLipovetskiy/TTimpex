<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<link rel="stylesheet" href="resources/css/style-scroll.css">
        <br/>
<%--        <button class="btn btn-primary" onclick="ctx.updateTable()">--%>
<%--            <span class="fa fa-plus"></span>--%>
<%--            <spring:message code="common.add"/>--%>
<%--        </button>--%>

        <div id="table-scroll" class="table-scroll">
            <div class="table-wrap">
                <table class="main-table table-bordered table-hover">
                    <thead>
                    <tr class="text-center">
                        <th rowspan="3" class="fixed-side align-middle" scope="col">№</th>
                        <th rowspan="3" colspan="4" class="fixed-side align-middle" scope="col">Name</th>
                        <th colspan="5" scope="col" class="align-middle">Avgust</th>
                    </tr>
                    <tr class="text-center">
                        <th scope="col">1</th><th scope="col">2</th><th scope="col">3</th><th scope="col">4</th><th scope="col">5</th>
                    </tr>
                    <tr class="text-center">
                        <th scope="col">пн</th><th scope="col">вт</th><th scope="col">ср</th><th scope="col">чт</th><th scope="col">пт</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <th rowspan="4" class="fixed-side">1</th>
                        <th rowspan="3" colspan="4" class="fixed-side">Іванов</th>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <%--                <td>Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td><td rowspan="2">Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>

                    <tr>
                        <th rowspan="4" class="fixed-side">2</th>
                        <th rowspan="3" colspan="4" class="fixed-side">Петров</th>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <%--                <td>Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td><td rowspan="2">Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <th rowspan="4" class="fixed-side">3</th>
                        <th rowspan="3" colspan="4" class="fixed-side">Ванечкін</th>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                        <td>Cell content Cell content Cell content</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <%--                <td>Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td><td rowspan="2">Іванов</td>--%>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>
                    <tr>
                        <%--                <td rowspan="4">1</td>--%>
                        <th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th><th class="fixed-side">10</th>
                        <td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td><td>9:00</td>
                    </tr>

                    </tbody>

                </table>
            </div>
        </div>

        <br><br>