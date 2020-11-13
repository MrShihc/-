<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/6
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- jstl库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 格式化日期库 --%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>独立考试</title>
</head>
<body>
    <center>
        <h1>考卷信息首页</h1>
        <table border="1" align="center" rules="all">
            <tr bgcolor="#00ffff" align="center">
                <th>考卷编号</th>
                <th>考卷名称</th>
                <th>总分</th>
                <th>通过分数</th>
                <th>考试时长</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>发布人</th>
                <th>操作<a href="/skip/goMakeTest.do">发布试题</a></th>
            </tr>
            <c:forEach var="test" items="${testList}">
                <tr align="center">
                    <td>${test.testid}</td>
                    <td>${test.testname}</td>
                    <td>${test.totalscore}</td>
                    <td>${test.passscore}</td>
                    <td>${test.testtime}</td>
                    <td>
                        <fmt:formatDate value="${test.starttime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td>
                        <fmt:formatDate value="${test.endtime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td><font color="red">${test.testauthor}</font></td>
                    <td><a href="#">查询试卷</a></td>
                </tr>
            </c:forEach>

        </table>
    </center>
</body>
</html>
