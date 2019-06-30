<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/26
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resultBean" class="course.CourseDesign_Bean" scope="request"/>
<html>
<head>
    <title>展示页面</title>
    <style type="text/css">
        body{
            text-align: center;
        }
    </style>
</head>
<body bgcolor=#DEEFF9>
<div style='text-align:left;'>
    <table border=1 style="margin:0px auto;">
        <%
            String[] columnName=resultBean.getColumnName();
        %>
        <tr>
            <%for(String s:columnName){
            %><th><%=s %></th>
            <% }
            %></tr>
        <%String[][] record=resultBean.getTableRecord();
            for(int i=0;i<record.length;i++){
        %><tr>
            <% for(int j=0;j<record[i].length;j++){
        %><td><%=record[i][j] %></td>
            <%}
        %>
            <%}
         %>
    </table>
    </div>
</body>
</html>
