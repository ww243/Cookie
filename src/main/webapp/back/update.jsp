<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/7
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台修改页面</title>
    <link rel="stylesheet" href="${page}/static/css/bootstrap.min.css"/>
    <script src="${page}/static/js/jquery-3.5.1.min.js"></script>
</head>
<body>



<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <h3 style="padding-left: 700px;color: red"><a href="${page}/back/login.jsp">${requestScope.message}</a></h3>
            <form method="post" action="${page}/cookie/updateCookie" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${byid.id}">
                <input type="hidden" name="putTime" value="${byid.putTime}">
                <input type="hidden" name="enterName" value="${byid.enterName}">
                <div class="form-group">
                    <label >ID:</label>
                    <input type="hidden" value="${requestScope.byid.id}" class="form-control" >${requestScope.byid.id}
                </div>
                <div class="form-group">
                    <label >菜谱名称:</label>
                    <input type="text" value="${requestScope.byid.name}" name="name" class="form-control" >
                </div>
                <div class="form-group">
                    <label >菜谱图片:</label>
                    <input type="file" class="form-control" name="photo">
                </div>
                <div class="form-group">
                    <label >菜谱关于:</label>
                    <input type="text" value="${requestScope.byid.about}" name="about" class="form-control" >
                </div>
                <div class="form-group">
                    <label >烹饪步骤:</label>
                    <textarea class="form-control" name="steps" cols="5" rows="10" >${byid.steps}</textarea>
                </div>
                <button type="submit" class="btn btn-info btn-block">录入菜谱</button>
                <a href="${page}/cookie/selectAll" type="button" class="btn btn-danger btn-block">返回列表</a>
            </form>
        </div>
    </div>

</div>

</body>
</html>
