<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/26
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
    <style type="text/css">
        .one{
            width:300px;/*宽度*/
            height:230px;/*高度*/
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
            width:200px;/*宽度*/
            height:150px;/*高度*/
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
<form action="${pageContext.request.contextPath }/register" method="post">
    <div id="web_bg" style="background-image: url(images/bg1.png);"></div>
    <div style="width:100%;text-align:center">
    <div class="one">
        <div class="two">
            <b>选择你要注册的类型:</b><br>
            <select name="person">
                <option value="4">管理员
                <option value="5">宿管员
                <option value="6">学生
            </select><br>
            <br>
            <input type="submit" value="注册" name="submit">
            <input type="reset" value="取消" name="reset">
        </div>
    </div>
    </div>
</form>
</body>
</html>
