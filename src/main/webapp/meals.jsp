<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://topjava.ru/functions/dateformatter" prefix="f" %>
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

    table {
        border-collapse: collapse;
        border: 1px solid black;
    }

    table td {
        border: 1px solid black;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="./NewMealServlet">Add meal</a>
<table>
  <tr>
    <th>Id</th>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
  </tr>
  <c:forEach var="mealTo" items="${mealTo}">
    <tr class="${mealTo.excess ? 'common' : 'alert'}">
      <td>${mealTo.id}</td>
      <td>${f:formatLocalDateTime(mealTo.dateTime, 'yyyy-MM-dd HH:mm')}</td>
      <td>${mealTo.description}</td>
      <td>${mealTo.calories}</td>
      <td><a href="">Update</a></td>
      <td><a href="">Delete</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>

