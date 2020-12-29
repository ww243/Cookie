<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/8
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>详情页面</title>
    <link rel="stylesheet" href="${page}/static/css/bootstrap.min.css"/>
    <script src="${page}/static/js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="container-fluid" style="padding-top: 40px;">

    <div class="panel panel-default" >
        <div class="panel-heading">
            <h3 class="panel-title">详细菜谱</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-sm-12">
                    <img src="${page}/img/${requestScope.cookie.src}" alt="...">
                    <div class="caption">
                        <h2>${requestScope.cookie.name}</h2>
                        <h4 class="text-info">关于:</h4>
                        <p style="margin-left: 30px;">${requestScope.cookie.about}</p>
                        <h4 class="text-danger">步骤:</h4>
                        <ul>
                            <li>${requestScope.cookie.steps}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <a href="${page}/qcookie/QselectAll" class="btn btn-success btn-block">返回主页</a>
        </div>
    </div>
</div>
</body>
</html>
