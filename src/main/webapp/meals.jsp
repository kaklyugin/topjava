<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html lang="ru">
<head>
  <title>Meals</title>
</head>
<style type="text/css">
    .common {
        color: red;
    }
    .alert {
        color: green;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
  <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
  </tr>
  <c:forEach var="mealTo" items="${mealTo}">
    <tr class="${mealTo.excess ? 'common' : 'alert'}">
      <td>${f:formatLocalDateTime(mealTo.dateTime, 'yyyy-MM-dd HH:mm')}</td>
      <td>${mealTo.description}</td>
      <td>${mealTo.calories}</td>
      <td><a href="url">Update</a></td>
      <td><a href="url">Delete</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>

