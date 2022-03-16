<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--禁用缓存-->
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <title>我的网盘</title>
    <link rel="icon" href="img/favicon.ico">
    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        body {
            display: flex;
            align-items: center; /*定义body的元素垂直居中*/
            justify-content: center; /*定义body的里的元素水平居中*/
        }

        .content {
            margin: 0 auto;
            width: 300px;
            height: 320px;
            border: black 1px solid;
            text-align: center;
        }
    </style>
</head>
<body>
<h1></h1>

<div class="content">
    <h1>文件上传</h1>
    <form method="post" action="${pageContext.request.contextPath}/file/upload" enctype="multipart/form-data"
          id="form">
        <input type="text" name="username" id="username" style="display: none"/><br>
        <input type="text" name="token" id="token" style="display: none"/><br>
        文件描述<textarea name="detail"></textarea><br>
        是否公开文件<input type="checkbox" name="isPublic"/><br>
        覆盖同名文件<input type="checkbox" name="isOverwrite"/><br>
        请选择一些文件（文件大小不要超过200MB）
        <br><br>
        <input type="file" name="files" multiple/>
        <br>
        <button type="button" onclick="f()">上传</button>
    </form>
</div>
<br>

<script>
    window.onload = function () {
        var Personalize = JSON.parse(localStorage.getItem("Personalize"));
        console.log(Personalize)
        if (Personalize != null) {
            document.getElementsByName("isPublic")[0].checked = Personalize.onPublic
            document.getElementsByName("isOverwrite")[0].checked = Personalize.onOverwrite

        }
    }

    function f() {
        if (document.getElementsByName("files")[0].value == "") {
            alert("请选择要上传的文件");
            return;
        }
        var userInfo = JSON.parse(localStorage.getItem("userInfo"))
        if (userInfo == null) {
            alert("您尚未登录");
            window.open("login.html", '_blank', 'width=500px,height=500px')
            return;
        }
        document.getElementById("username").value = userInfo.username
        document.getElementById("token").value = userInfo.token
        document.getElementById("form").submit()
    }
</script>
</body>
</html>