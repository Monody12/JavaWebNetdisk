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
<h2 style="color: red" id="info"></h2>
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
            <td>${file.detail}</td>
            <td><span id="${fileStatus.step}">${file.size}</span></td>
            <td>
                <a href="../download/${file.id}?token=${token}" download="${file.name}">点击下载</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="../../../index.html">返回我的个人网盘</a></p>
<script type="text/javascript">
    var _1MB = 1024 * 1024;
    var _1KB = 1024;
    window.onload = function () {
        console.log("执行了window.onload")
        var spans = document.getElementsByTagName("span");
        setTimeout(() => {
            alert("页面过期，请刷新！")
            document.getElementById("info").innerText = "文件链接已过期，请刷新！"
        }, 60 * 5 * 1000)
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
