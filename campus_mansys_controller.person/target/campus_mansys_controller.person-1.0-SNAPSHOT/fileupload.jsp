<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/5
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传试题</title>
</head>
<body>
    <form action="user/fileUpload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="filename" />
        <input type="submit" value="上传" />
    </form>
</body>
</html>
