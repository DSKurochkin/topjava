<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2><a href="/topjava">Home</a></h2>
<hr align="left" size="2" color="black"/>
<h2>
    <c:if test="${meal != null}">
        Edit meal
    </c:if>
    <c:if test="${meal == null}">
        Add meal
    </c:if>
</h2>
<br>
<form method="post" action="save">
    <c:if test="${meal != null}">
        <input type="hidden" name="id" value="<c:out value='${meal.id}'/>"/>
    </c:if>
    <label for="date">DateTime</label>
    <input type="datetime-local" name="datetime-local" id="date"
           value="<c:out value='${meal.dateTime}'/>"
    />

    <br>
    <label for="description">Description</label>
    <input type="text" name="description" id="description" size="35"
           value="<c:out value='${meal.description}'/>"
    />
    <br>
    <label for="calories">Calories</label>
    <input type="text" name="calories" id="calories"
           value="<c:out value='${meal.calories}'/>"
    />
    <br><br>
    <input type="submit" value="Ok">
    <input type="button" value="Cancel"
           onclick="window.location.href='/topjava/meals'">
</form>
</body>
</html>