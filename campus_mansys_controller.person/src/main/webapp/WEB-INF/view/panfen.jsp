<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/23
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>学生判分界面</title>
</head>
<body>

<form action="/teacher/saveTestStudentQuestion.do" method="post">
    <input type="hidden" name="crediskey" value="${crediskey}">
    <c:forEach var="exam" items="${examList}" varStatus="status">
        <table>
            <tr>
                <td colspan="5px">
                        ${status.index+1}、${exam.etype}(${exam.efenzhi})
                    <input type="hidden" name="examids" value="${exam.examid}">
                            <font color="red">老师打分： </font><input type="number" name="fenzhis"
                                 onblur="getfenzhi(${exam.efenzhi},this)" style="width:70px;">
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>${exam.ename}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td><font color="aqua">学生作答</font>
                    <textarea readonly>${exam.myanswer}</textarea>
                </td>
            </tr>
        </table>
    </c:forEach>

</form>

<input type="button" value="提交" onclick="toSubmit()">
</body>
</html>
<%-- 编写自己的js代码 --%>
<script type="text/javascript">
    function getfenzhi(efenzhi, obj) {
        if (obj.value > efenzhi) {
            alert("所判分不能超过总分！");
            obj.focus();
        }
    }

    function toSubmit() {

        var flag = true;
        var fenzhis = document.getElementsByName("fenzhis");
        for (var i = 0; i < fenzhis.length; i++) {
            if (fenzhis[i].value == null || fenzhis[i].value == "") {
                alert("有题目没有判分");
                fenzhis[i].focus();
                flag = false;
                break;
            }
        }

        //判断是否有题未判
        if (flag) {
            document.forms[0].submit();
        }
    }

</script>
