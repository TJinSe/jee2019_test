<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/28
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员删除界面</title>
    <style type="text/css">
        .one{
            width:200px;/*宽度*/
            height:200px;/*高度*/
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
        .two{
            width:200px;/*宽度*/
            height:100px;/*高度*/
            text-align:center;
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
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath }/SAdminsDelete" method="post">
    <div id="web_bg" style="background-image: url(images/bg3.png);"></div>
    <div class="one">
        <div class="two">
            <%="管理员删除信息界面" %><br><br>
            <input type="submit" value="deleteStu" name="delete" ><%--删除学生roomStu表 --%>
            <input type="submit" value="deleteRoom" name="delete" ><%--删除宿管员roomAdmins表 --%>
        </div>
    </div>
</form>
</body>
</html>
