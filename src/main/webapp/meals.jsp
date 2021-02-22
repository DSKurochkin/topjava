<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://localhost:8080/topjava/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h2><a href="/topjava">Home</a></h2>
<hr align="left" size="2" color="black"/>
<h2>Meals</h2>
<a href="/topjava/meals/add">Add meals</a>
<br><br>
<table border=1>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <c:url var="editButton" value="meals/add">
            <c:param name="mealId" value="${meal.id}"/>
        </c:url>
        <c:url var="delButton" value="meals/delete">
            <c:param name="mealId" value="${meal.id}"/>
        </c:url>
        <tr style="color:${(meal.excess == true ? 'red' : 'green')}">
            <td>
                    ${f:convertLocalDateTimeInString(meal.dateTime)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <input type="button" value="Edit"
                       onclick="window.location.href='${editButton}'">
            </td>
            <td>
                <input type="button" value="Delete"
                       onclick="window.location.href='${delButton}'">
            </td>

        </tr>

    </c:forEach>

</table>

</body>
</html>