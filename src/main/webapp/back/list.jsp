<%--
  Created by IntelliJ IDEA.
  User: 楠少
  Date: 2020/11/7
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>后台列表页面</title>
    <link rel="stylesheet" href="${page}/static/css/bootstrap.min.css"/>
    <!--热词处理-->
    <style>
        .alert-warning{
            width: 160px;
            float: left;
            margin-right: 10px;
            height: 50px;
        }
    </style>
    <script src="${page}/static/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#closeIndex').click(function () {
                $.ajax({
                    url: '${page}/cookie/closeIndex',
                    type: "POST",
                    success: function (data) {
                        alert(data.yes);
                    },
                    error: function (data) {
                        alert(data.no)
                    },
                    dataType: 'json'
                });
            });
            $('#addIndex').click(function () {
                $.ajax({
                    url: '${page}/cookie/addIndex',
                    type: "POST",
                    success: function (data) {
                        alert(data.yes);
                    },
                    error: function (data) {
                        alert(data.no)
                    },
                    dataType: 'json'
                });
            });
        })
    </script>
</head>
<body>

<!--功能按钮-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">后台食谱管理系统</a>
        </div>
        <!--功能按钮 -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left">
                <button type="button" class="btn btn-danger" id="closeIndex">清空ES索引库</button>
                <button type="button" class="btn btn-info" id="addIndex">基于mysql数据重建索引</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="text-info">${sessionScope.user.username}</span></a></li>
                <li><a href="${page}/cookie/exit">退出系统</a></li>
            </ul>
        </div>
    </div>
</nav>

<h1 style="margin-left: 20px">全网热搜</h1>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6">
            <div style="padding: 10px 20px;" id="Qhotsearch">
                <script>
                    $(function () {
                        Qhotsearch();
                        function Qhotsearch(){
                            $.ajax({
                                url: '${page}/qcookie/Qhotsearch',
                                type: 'POST',
                                success:function (data) {
                                    $.each(data,function (key,value) {
                                        console.log(key);
                                        console.log(value);
                                        let span1=$('<span/>').css('margin','5px 2px');
                                        span1.html(key+"&nbsp;");
                                        let span = $('<span class="badge"/>');
                                        span.text(value);
                                        if(value>0&&value<20) span1.attr('class','label label-primary' );
                                        else if(value>=20&& value<30) span1.attr('class','label label-success' );
                                        else if(value>=30&& value<40) span1.attr('class','label label-info' );
                                        else if(value>=40&& value<50) span1.attr('class','label label-warning' );
                                        else if(value>=50) span1.attr('class','label label-danger' );
                                        span1.append(span);
                                        $('#Qhotsearch').append(span1);
                                    })

                                }
                            })
                        }
                    })
                </script>
                <%--<span class="label label-danger">西红柿 <span class="badge">56</span></span>
                <span class="label label-warning">辣椒 <span class="badge">44</span></span>
                <span class="label label-info">羊肉 <span class="badge">34</span></span>
                <span class="label label-success">牛肉 <span class="badge">24</span></span>
                <span class="label label-primary">青菜 <span class="badge">12</span></span>--%>
            </div>
        </div>
        <div class="col-sm-6">
            <!--远程热词管理-->
            <form class="form-horizontal" style="text-align: center">
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="exampleInputEmail3">输入远程扩展词典</label>
                    <div class="col-sm-4">
                        <input type="text"  name="content" class="form-control" id="exampleInputEmail3" placeholder="请输入扩展词典">
                    </div>
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-info" id="QhotsDic" >添加到远程词典</button>
                    </div>
                </div>
            </form>
            <div id="QgetHots"></div>
            <script>
                $(function () {
                    $('#QhotsDic').click(function () {
                        let a= $('#exampleInputEmail3').val();
                        $.ajax({
                            url: '${page}/qcookie/QhotsDic',
                            type: 'POST',
                            data:{'content':a},
                            success:function (data) {
                                QgetHots();
                                console.log(data.yes);
                                alert(data.yes);
                            },
                            error:function (data) {
                                console.log(data.no);
                                alert(data.no);
                            }
                        });
                    });
                    QgetHots();
                    function QgetHots() {
                        $.ajax({
                            url: '${page}/qcookie/QgetHots',
                            type: 'POST',
                            success:function (data) {
                                $('#QgetHots').empty();
                                $.each(data,function (key,value) {
                                    let div=$('<div class="alert alert-warning alert-dismissible text-center" role="alert" />')
                                    let button=$('<button type="button" class="close" data-dismiss="alert" aria-label="Close" />');
                                    let span=$('<span aria-hidden="true"/>');
                                    let span1=$('<strong/>');
                                    console.log(key);
                                    console.log(value);
                                    let text=span1.text(value);
                                    span.html("&times;").click(function () {
                                        let del=$(this).prev().text();
                                        console.log(del);
                                        $.ajax({
                                           url:'${page}/qcookie/QdelHots',
                                           data:{'content':value},
                                           type:'POST',
                                           success:function (data) {
                                               QgetHots();
                                               alert("清楚成功。");
                                           },
                                            dateType: 'json'
                                        });
                                    });
                                    button.append(span);
                                    div.append(button);
                                    div.append(span1);
                                    $('#QgetHots').append(div);
                                })
                            }
                        });
                    }
                })
            </script>
            <!--远程词典列表-->
            <%--<div class="alert alert-warning alert-dismissible text-center" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>碰瓷</strong>
            </div>--%>
        </div>
    </div>
</div>

    <div class="row">
        <div class="col-sm-12">
            <a href="${page}/back/add.jsp" class="btn btn-info">添加</a>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <div class="col-sm-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>菜谱名称</th>
                    <th>图片</th>
                    <th>录入时间</th>
                    <th>录入人</th>
                    <th>关于摘要</th>
                    <th>步骤摘要</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.cookies}" var="a">
                <tr>
                    <th scope="row">${a.id}</th>
                    <td>${a.name}</td>
                    <td><img style="width: 120px;height: 60px;" src="${page}/img/${a.src}" class="img-thumbnail" alt=""></td>
                    <td><fmt:formatDate value="${a.putTime}"></fmt:formatDate></td>
                    <td>${a.enterName}</td>
                    <td>${a.about}</td>
                    <td>${a.steps}</td>
                    <td><a href="${page}/cookie/deleteCookie?id=${a.id}" class="btn btn-danger">删除</a>&nbsp;&nbsp;
                        <a href="${page}/cookie/selectByidCookie?id=${a.id}" class="btn btn-info">修改</a></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
