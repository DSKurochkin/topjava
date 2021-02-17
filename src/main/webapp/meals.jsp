<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://localhost:8080/topjava/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h2>Meals</h2>
<br>
<table border=1>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:${(meal.excess == true ? 'red' : 'green')}">

            <td>
            ${f:convertLocalDateTimeInString(meal.dateTime)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>

    </c:forEach>

</table>

</body>
</html>