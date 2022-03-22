<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: monody
  Date: 2022/3/21
  Time: 8:25 下午
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>分享文件列表</title>
</head>
<body>
<table border="1">
    <tr>
        <th>文件名</th>
        <th>文件类型</th>
        <th>文件描述</th>
        <th>文件大小</th>
        <th>下载链接</th>
    </tr>
    <jsp:useBean id="files" scope="request" type="java.util.List"/>

    <c:forEach var="file" items="${files}" varStatus="fileStatus" step="1">
        <tr>
            <td>${file.name}</td>
            <td>${file.type}</td>
            <td><span id="${fileStatus.step}">${file.detail}</span></td>
            <td>${file.size} 字节</td>
            <td>
                <form method="post" action="../download/${file.id}">
                    <input name="token" value="${token}" hidden>
                    <button type="submit">立即下载</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<script type="text/javascript">
    var _1MB = 1024 * 1024;
    var _1KB = 1024;
    window.onload = function () {
        var spans = document.getElementsByClassName("span");
        for (var i = 0; spans !== undefined && i < spans.length; ++i) {
            var size = spans[i].innerHTML;
            if (size > _1MB) {
                size = (size / _1MB).toFixed(2) + " MB";
            } else if (size > _1KB) {
                size = (size / _1KB).toFixed(2) + " KB";
            } else {
                size = (size / 1).toFixed(2) + " B";
            }
            spans[i].innerHTML = size;
        }
    }
</script>
</body>
</html>
