<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://topjava.ru/functions/dateformatter" prefix="f" %>
<html lang="ru">
<head>
  <title>Update meal</title>
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
<h2>Update meal with id = ${mealForUpdate.id} </h2>
<form action='UpdateMealServlet' method="POST">
  <input type="hidden" name="mealId" value=${mealForUpdate.id}>
  DateTime: <input type="datetime-local" name="meal_date"
                   value="${f:formatLocalDateTime(mealForUpdate.dateTime, 'dd.MM.yyyy HH:mm')}"
                   pattern="dd.MM.yyyy HH:mm"
>
  <br/>
  Description: <input type="text" name="description" value= ${mealForUpdate.description}>
  <br/>
  Calories: <input type="text" name="calories" value= ${mealForUpdate.calories}>
  <br/>
  <br/>
  <input type="submit" value="Update"/>
</form>
<button onclick="window.history.back()" type="button">Cancel</button>
</body>
</html>

