<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<%--    <form method="get" action="meals/filter">--%>
<%--    <form method="get">--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.startDate"/>:</dt>--%>
<%--            <dd id="startDate"><input type="date" name="startDate" value="${param.startDate}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.endDate"/>:</dt>--%>
<%--            <dd id="endDate"><input type="date" name="endDate" value="${param.endDate}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.startTime"/>:</dt>--%>
<%--            <dd id="startTime"><input type="time" name="startTime" value="${param.startTime}"></dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt><spring:message code="meal.endTime"/>:</dt>--%>
<%--            <dd id="endTime"><input type="time" name="endTime" value="${param.endTime}"></dd>--%>
<%--        </dl>--%>
<%--        <button id="filter" type="submit"><spring:message code="meal.filter"/></button>--%>
<%--    </form>--%>


<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>
        <br>
        <%--        -----------------FILTER----------------%>
        <div class="container">
            <form name="filter">
                <div class="form-row">
                    <div class="col">
                        <dl>
                            <dt><spring:message code="meal.startDate"/>:</dt>
                            <dd><label>
                                <input id ="startDate" name="startDate" type="date">
                            </label></dd>
                        </dl>
                    </div>
                    <div class="col">
                        <dl>
                            <dt><spring:message code="meal.endDate"/>:</dt>
                            <dd><label>
                                <input id= "endDate" name="endDate" type="date">
                            </label></dd>
                        </dl>
                    </div>
                    <div class="col">
                        <dl>
                            <dt><spring:message code="meal.startTime"/>:</dt>
                            <dd><label>
                                <input id = "startTime" name="startTime" type="time">
                            </label></dd>
                        </dl>
                    </div>
                    <div class="col">
                        <dl>
                            <dt><spring:message code="meal.endTime"/>:</dt>
                            <dd><label>
                                <input id="endTime" name="endTime" type="time">
                            </label></dd>
                        </dl>
                    </div>
                </div>

            </form>
            <div class="text-right">
                <button class="btn btn-dark" onclick="test()">
                    <span class="fa fa-telegram"></span>
                    <p>isFiltered</p>
                </button>

                <button class="btn btn-danger" onclick="off_filter()">
                    <span class="fa fa-undo"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="on_filter()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="meal.filter"/>
                </button>
            </div>


        </div>
        <%--        -----------------END FILTER----------------%>
        <br>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr id="${meal.id}" data-mealExcess="${meal.excess}">
                    <td> ${fn:formatDateTime(meal.dateTime)}</td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="user.name"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="text" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
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
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>