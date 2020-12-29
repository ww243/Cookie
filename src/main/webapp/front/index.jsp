<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/8
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>前台首页</title>
    <link rel="stylesheet" href="${page}/static/css/bootstrap.min.css"/>
    <script src="${page}/static/js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <!--搜索框-->
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12" style="text-align: center">
            <form class="form-inline" action="${page}/qcookie/QselectAll" method="post" enctype="multipart/form-data">
                <div class="form-group" style="width: 600px;">
                    <input type="text" style="width: 600px;" class="form-control" placeholder="请输入查询条件" name="content">
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
        </div>
    </div>
    <h1 class="page-header">搜索结果</h1>
    <!--搜索列表-->
    <div class="row" style="margin-top: 20px">
        <c:forEach items="${requestScope.all}" var="aa">
        <div class="col-sm-3">
            <div class="thumbnail">
                <img src="${page}/img/${aa.src}" class="img-circle" style="width: 200px;height: 120px;">
                <div class="caption">
                    <h3 class="text-center">${aa.name}</h3>
                    <p>${aa.about}</p>
                    <p><a href="${page}/qcookie/QselectByid?id=${aa.id}" class="btn btn-danger btn-block" role="button">查看详细</a></p>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>

    <!--分页工具栏-->
    <nav aria-label="Page navigation">
        <ul class="pagination pull-right">
            <li>
                <a <c:if test="${requestScope.pageNow!=null && requestScope.pageNow > 1}">
                    href="${page}/qcookie/QselectAll?pageNow=${requestScope.pageNow-1}"</c:if> aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="1" end="${requestScope.total}" varStatus="vs">
                <li><a href="${page}/qcookie/QselectAll?pageNow=${vs.count}">${vs.count}</a></li>
            </c:forEach>
            <li>
                <a <c:if test="${requestScope.pageNow < requestScope.total}">
                    href="${page}/qcookie/QselectAll?pageNow=${requestScope.pageNow+1}"</c:if> aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            <li><button type="submit" class="btn btn-success" onclick="location.href='${page}/qcookie/QselectAll'">返回列表</button></li>
        </ul>
    </nav>

</div>
</body>
</html>
