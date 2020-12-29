<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/7
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台列表页面</title>
    <link rel="stylesheet" href="${page}/static/css/bootstrap.min.css"/>
    <script src="${page}/static/js/jquery-3.5.1.min.js"></script>
</head>
<body>



<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <h3 style="padding-left: 700px;color: red"><a href="${page}/back/login.jsp">${requestScope.message}</a></h3>
            <form action="${page}/cookie/insertCookie" enctype="multipart/form-data" method="post">
                <div class="form-group">
                    <label>菜谱名称:</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="name">
                </div>
                <div class="form-group">
                    <label >菜谱图片:</label>
                    <input type="file" class="form-control" name="photo">
                </div>

                <div class="form-group">
                    <label >菜谱关于:</label>
                    <input type="text" class="form-control" name="about">
                </div>

                <div class="form-group">
                    <label >烹饪步骤:</label>
                    <input type="text" class="form-control" name="steps">
                </div>
                <button type="submit" class="btn btn-info btn-block">录入菜谱</button>
                <a href="${page}/cookie/selectAll" type="button" class="btn btn-danger btn-block">返回列表</a>
            </form>
        </div>
    </div>

</div>

</body>
</html>