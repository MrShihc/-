<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/23
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>老师阅卷界面</title>
</head>
<body>
    <center>
        <table border="1" rules="all">
            <tr><h1>老师阅卷界面</h1></tr>
            <tr>
                <th>编号</th>
                <th>学生姓名</th>
                <th>总分数</th>
                <th>客观分数</th>
                <th>主观分数</th>
                <th>操作</th>
            </tr>
            <c:forEach var="t" items="${testList}" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${t.sname}</td>
                    <td>${t.score}</td>
                    <td>${t.cscore}</td>
                    <td>${t.qscore}</td>
                    <td>
                        <c:if test="${t.qscore==null || t.qscore==''}">
                            <a href="/teacher/toPanfen.do?crediskey=${t.crediskey}">判分</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </center>
</body>
</html>