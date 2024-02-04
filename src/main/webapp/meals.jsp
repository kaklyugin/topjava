<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
  <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<ul>
  <c:forEach var="meal" items="${meals}">
    <li><c:out value="${meal.calories}"/></li>
  </c:forEach>
</ul>
</body>
</html>