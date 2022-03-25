<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/7/31
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误反馈页面</title>
    <link rel="icon" href="img/favicon.ico">
</head>
<body>

    <h1>很不幸，页面发生了错误！</h1>
    <h2>页面发生如下异常：${errorInfo}</h2>
    <%
        String errorType = (String) request.getAttribute("errorType");
        if ("null".equals(errorType)){
            out.println("<h3>请检查您提交的内容是否正确，且无遗漏。<br>通常您可以通过注销后再次登录账号来解决此问题。<br>如果此问题反复出现，请联系网站管理员。</h3>");
        }else if ("other".equals(errorType)){
            out.println("<h3>服务器内部发生了异常。<br>您可以尝试退出登录后重新执行操作，如果问题依然出现，请联系网站管理员解决。</h3>");
        }
        out.println("<br><h4>联系邮箱：qq614908309@gmail.com</h4>");
    %>
</body>
</html>
