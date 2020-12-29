<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/7
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<html lang="en">

<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${page}/static/css/bootstrap.min.css"/>
</head>

<body>
<div id="wrap" class="container-fluid">
    <div id="top_content" class="row"  style="margin: 0 auto;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="header">
                <div id="topheader">
                    <h1 class="text-center text-info">欢迎进入菜谱管理系统v1.0</h1>
                </div>
                <h2>${sessionScope.message}</h2>
                <div id="navigation">
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-8 col-sm-offset-2">
            <div id="content">
                <form method="post" action="${page}/user/login">
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text"  v-model="username" id="username" class="form-control" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="password"  v-model="password"  class="form-control" name="password"/>                    </div>
                    <br>
                    <input type="submit" style="width: 98%"   class="btn btn-danger" value="登录&raquo;"/>
                </form>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 40px;">
        <div class="col-sm-8 col-sm-offset-2">
            <h5 class="text-center">cookie book @136.com</h5>
        </div>
    </div>
</div>
</body>
</html>

