<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/29
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员删除宿管</title>
    <style type="text/css">
        .one{
            width:400px;/*宽度*/
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
            width:400px;/*宽度*/
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
<form action="${pageContext.request.contextPath }/SAdminsDeleteRoom" method="post">
    <div id="web_bg" style="background-image: url(images/bg9.jpg);"></div>
    <div style="width:100%;text-align:center">
    <s:fielderror/>
    <div class="one">
        <div class="two">
            <%="管理员删除宿管信息界面" %><br><br>
            账号：<input type="text" name="number" ><%--删除宿管roomAdmins表 --%><br><br>
            <input type="submit" value="确定" name="submit" >
            <input type="reset" value="取消" name="reset">
        </div>
    </div>
    </div>
</form>
</body>
</html>