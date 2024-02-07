<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://topjava.ru/functions/dateformatter" prefix="f" %>
<html lang="ru">
<head>
  <title>New meal</title>
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
<h2>New meal</h2>
<br/>
<form action='NewMealServlet' method="POST">
  <label for="birthdaytime"></label>
  DateTime: <input type="datetime-local" name="meal_date">
  <br/>
  Description: <input type="text" name="description">
  <br/>
  Calories: <input type="text" name="calories"/>
  <br/>
  <hr>
  <input type="submit" value="Save"/>
</form>
</body>
</html>

