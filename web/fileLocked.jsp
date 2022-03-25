<%--
  Created by IntelliJ IDEA.
  User: monody
  Date: 2022/3/19
  Time: 8:36 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="fileLocked" class="com.netdisk.entity.vo.FileLocked" scope="request"/>
<html>
<head>
    <title>我的个人网盘——提取文件</title>
</head>
<body>

<h2><jsp:getProperty name="fileLocked" property="username"/> 给您加密分享了文件，请输入提取码后进行查看：</h2>
<p style="color: red"><jsp:getProperty name="fileLocked" property="message"/></p>
<form method="get" action="<jsp:getProperty name="fileLocked" property="fileLink"/> ">
    <input name="code" type="text">
    <input name="applicationName" type="text" value="netdisk_Web_exploded" hidden>
    <button type="submit">提交</button>
</form>
</body>
</html>


