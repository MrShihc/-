<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/20
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>阅卷管理界面</title>
</head>
<body>
    <center>
        <table border="1" rules="all" align="center">
            <tr><th colspan="10px"><h1>阅卷管理界面</h1></th></tr>
            <tr align="center">
                <th>编号</th>
                <th>试卷名称</th>
                <th>所在班级</th>
                <th>考试人数</th>
                <th>待审阅人数</th>
                <th>操作</th>
            </tr>
            <c:forEach var="test" items="${readInfo}" varStatus="statu">
                <tr align="center">
                    <td>${statu.index+1}</td>
                    <td>${test.testname}</td>
                    <td>${test.gname}</td>
                    <td>${test.totalcount}人</td>
                    <td>${test.nocount}人</td>
                    <td>
                        <a href="/teacher/toReadTestStudent.do?gid=${test.gid}&testid=${test.testid}">去阅卷</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </center>
</body>
</html>
