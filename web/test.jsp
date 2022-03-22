<%--
  Created by IntelliJ IDEA.
  User: monody
  Date: 2022/3/20
  Time: 11:50 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        ${word}
    </title>
</head>
<body>
<%
    String word = request.getParameter("word");
    out.println(word);
%>
    <h3>${word}</h3>
</body>
</html>
