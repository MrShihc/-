<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/24
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>老师登录界面</title>
</head>
<body>
    <center>  <!-- /teacher/toTeacherLogin.do -->
        <form action="" method="post">
            账户名：<input type="text" name="taccount"><br>
            密码：<input type="password" name="pwd"><br>
            <input type="button" onclick="toLogin()" value="登录">
            <input type="reset" value="重置">
        </form>
    </center>
</body>
</html>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<!-- 编写自己的js代码 -->
<script type="text/javascript">
    function toLogin(){

        var data = $("form").serialize();
        console.log(data);
        $.post("/teacher/toTeacherLogin.do?"+data,function(response){
            if(response.flag){
                alert(response.message);
                location.href="/teacher/toMain.do";
            }else{
                alert(response.message);
            }
        });
    }
</script>
