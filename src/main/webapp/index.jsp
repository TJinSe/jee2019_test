<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/26
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <style type="text/css">

        .one{
            width:400px;/*宽度*/
            height:260px;/*高度*/
            text-align:center;
            background:#FFC;
            border-style:solid solid solid solid;
            border-width:1px 1px 1px 1px;
            position:absolute;
            left:0;
            top:0;
            right:0;
            bottom:0;
            margin:auto;
        }

        #web_bg{
            position:fixed;
            top: 0;
            left: 0;
            width:100%;
            height:100%;
            min-width: 1000px;
            z-index:-10;
            zoom: 1;
            background-color: #fff;
            background-repeat: no-repeat;
            background-size: cover;
            -webkit-background-size: cover;
            -o-background-size: cover;
            background-position: center 0;
        }

        .two{
            width:400px;/*宽度*/
            height:230px;/*高度*/
            text-align:center;
            position:absolute;
            left:0;
            top:0;
            right:0;
            bottom:0;
            margin:auto;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath }/loginAction" method="post">
    <div id="web_bg" style="background-image: url(images/bg4.png);"></div>
    <div style="width:100%;text-align:center">
        <s:fielderror/>
    <div class="one">
        <div class="two">
            <%="登录界面" %><br><br>
            账号：<input type="text" name="ID"><br><br>
            密码：<input type="text" name="password"><br><br>
            身份：<input type="radio" value="admins" name="name">管理员
            <input type="radio" value="room" name="name">宿管
            <input type="radio" value="student" name="name">学生<br><br>
            <input type="submit" value="登录" name="login">
            <input type="reset" value="取消" name="reset"><br><br>
            <a href="register.jsp">还没有账号？去注册一个</a>
        </div>
    </div>

    </div>
</form>
</body>
</html>
