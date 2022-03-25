<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>文件上传结果</title>
    <link rel="icon" href="img/favicon.ico">
    <style type="text/css">
        .list {
            margin: 0 0 0 0;
            padding: 0 0 0 0;
        }
    </style>
</head>
<body>
<%
    int exist_cnt = (int) request.getAttribute("exist_cnt");  //因文件名重复上传失败的文件数量
    int overwrite_cnt = (int) request.getAttribute("overwrite_cnt");  //被覆盖的文件数量
    int count = (int) request.getAttribute("count");  //成功上传文件数量
    int all_count = (int) request.getAttribute("all_count");  //总共提交的文件数量
    Boolean isOverwrite = (Boolean) request.getAttribute("isOverwrite");
    List<String> err_file = (List<String>) request.getAttribute("err_file");  //发生错误的文件名
    List<String> overwrite_file = (List<String>) request.getAttribute("overwrite_file");  //发生覆盖的文件名
%>
<center>
    <h2>${message}</h2><br>
    <h3>共请求上传${all_count}个文件，已成功上传${count}个文件</h3><br>
    <%
        if (!isOverwrite) {
            if (exist_cnt > 0) {
                out.println("<h3 style=\"color: #ff0000\">其中有<b>" + exist_cnt + "</b>个文件因为文件名重复上传失败</h3>");
                for (String s : err_file) {
                    out.println("<h4 class=\"list\">" + s + "</h4><br>");
                }
            } else if (all_count == count) {
                out.println("<h4>3秒后页面自动关闭</h4>");
                out.println("<script type=\"text/javascript\">setTimeout(\"self.close()\", 3000);</script>");
            }
        } else {
            if (overwrite_cnt > 0) {
                out.println("<h3 style=\"color: #ff0000\">其中有<b>" + overwrite_cnt + "</b>个文件因为文件名重复被覆盖</h3>");
                for (String s : overwrite_file) {
                    out.println("<h4 class=\"list\">" + s + "</h4><br>");
                }
            } else if (all_count == count) {
                out.println("<h4>3秒后页面自动关闭</h4>");
                out.println("<script type=\"text/javascript\">setTimeout(\"self.close()\", 3000);</script>");
            }
        }
    %>
</center>
<script type="text/javascript">
    window.onload = function () {

    }

    function closeit() {
        setTimeout("self.close()", 3000);
    }
</script>

</body>
</html>