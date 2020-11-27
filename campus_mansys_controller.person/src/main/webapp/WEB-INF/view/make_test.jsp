<%--
  Created by IntelliJ IDEA.
  User: 石海超Mr.SHI
  Date: 2020/11/6
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 引入jstl --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 引入jQuery库 --%>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>

<%-- 引入jQuery库 --%>
<script type="text/javascript" src="../../vuejs/vuejs-2.5.16.js"></script>
<script type="text/javascript" src="../../vuejs/axios-0.18.0.js"></script>
<%--<script type="text/javascript" src="../../vuejs/test_mark.js"></script>--%>
<%-- 编写自己的js代码 --%>
<script type="text/javascript">

    //定义一个6位随机数
    var random = "";
    for (var i = 0; i < 6; i++) {
        random += Math.floor(Math.random() * 10);
    }

    var flag = false;

    /**
     * 验证考试名称的唯一性
     */
    function uniTestname() {
        //获取考试名称的值
        var testname = $("#testname").val();
        if (testname == "") {
            $("#name").css("color", "red");
            $("#name").text("考试名称不能为空！");
        } else {

            $.ajax({
                url: "/teacher/uniTestname.do?" + random,     //请求路径
                type: "post",     //请求方式
                data: {
                    testname: testname
                },      //传到后台的数据
                dataType: "json",        //服务器返回的数据类型
                success: function (result) {
                    if (result.success == "success") {
                        $("#name").css("color", "green");
                        $("#name").text(result.message);
                        flag = true;
                    } else {
                        $("#name").css("color", "red");
                        $("#name").text(result.message);
                        $("#testname").focus();
                    }
                }
            });
        }
    }


    //上传Excel表格
    function importTest() {
        var formData = new window.FormData();
        formData.append("filename", document.querySelector('input[type=file]').files[0]);
        $.ajax({
            url: "/file/fileUpload.do?" + random,    //请求路径
            type: "post",       //请求方式
            data: formData,      //返回到后台的数据
            dataType: "json",    //服务器返回的数据类型
            cache: false,        //是否缓存
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.success == "success") {
                    $("#file").css("color", "green");
                    $("#file").text("上传成功！");
                    $("[name='totalscore']").val(data.data2);
                    flag = true;
                } else {
                    $("#file").css("color", "red");
                    $("#file").text(data.message);
                }
            }
        });
    }

    //发布试题
    function makeTest() {
        // var form = $("form").serialize();

        //考卷名称
        var testname = $("#testname").val();
        if (testname == "" || testname == null) {
            $("#name").css("color", "red");
            $("#name").text("考试名称不能为空哦！");
        }
        //总分数
        var totalscore = $("#totalscore").val();
        //通过分数
        var passscore = $("#passscore").val();
        //考试时长
        var testtime = $("#testtime").val();

        // //考试对应班级id
        // var gids = "";
        // var gid = $("[name='gids']:checked");
        // for (let i = 0; i < gid.length; i++) {
        //     gids += gid[i].value + ",";
        // }
        // gids = gids.substring(0, gids.length - 1);

        //考试开始时间
        var starttimeS = $("#starttime").val();

        //获取当前的一个Date类型 用于讲选择的时间存放
        var statime = new Date();
        statime.setFullYear(parseInt(starttimeS.substring(0, 4)));
        statime.setMonth(parseInt(starttimeS.substring(5, 7)) - 1);
        statime.setDate(parseInt(starttimeS.substring(8, 10)));
        statime.setHours(parseInt(starttimeS.substring(11, 13)));
        statime.setMinutes(parseInt(starttimeS.substring(14, 16)));
        // alert(statime)

        //考试结束时间
        var endtime = $("#endtime").val();
        //获取当前的一个Date类型 用于讲选择的时间存放
        var entime = new Date();
        entime.setFullYear(parseInt(endtime.substring(0, 4)));
        entime.setMonth(parseInt(endtime.substring(5, 7)) - 1);
        entime.setDate(parseInt(endtime.substring(8, 10)));
        entime.setHours(parseInt(endtime.substring(11, 13)));
        entime.setMinutes(parseInt(endtime.substring(14, 16)));

        if (flag == true) {
            //发送ajax请求，保存试题
            $.ajax({
                url: "/teacher/makeTest.do",    //请求路径
                type: "post",       //请求方式
                data: {
                    testname: testname,
                    totalscore: totalscore,
                    passscore: passscore,
                    starttime: statime,
                    endtime: entime,
                    testtime: testtime,
                },      //返回到后台的数据
                dataType: "json",    //服务器返回的数据类型
                success: function (data) {
                    if (data.success == "success") {
                        alert(data.message);
                        location.href = "/teacher/getDuliTestList.do";
                    } else {
                        alert(data.message);
                    }
                }
            });
        } else {

        }
    }
</script>
<html>
<head>
    <title>发布试题界面</title>
</head>
<body>
<center>
    <div id="vuediv">
        <h1>发布试题界面</h1>
        <form action="/teacher/goMakeTest.do" method="post" enctype="multipart/form-data" onsubmit="return check_all()">
            <table border="1" rules="all" align="center">
                <tr>
                    <td>考试名称:</td>
                    <td>
                        <input type="text" name="testname" id="testname" onblur="uniTestname()" placeholder="用户名不能重复哦！">
                        <span><font id="name"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>导入试题：</td>
                    <td>
                        <input type="file" name="filename">
                        <input type="button" value="上传" onclick="importTest()">
                        <span><font id="file"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>总分</td>
                    <td>
                        <input type="text" name="totalscore" id="totalscore" placeholder="上传试题之后自动生成" readonly>
                        <span><font id="total"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>通过分数</td>
                    <td>
                        <input type="text" name="passscore" onblur="panDuanScore()" id="passscore" placeholder="通过分数不能为空哦！">
                        <span><font id="pass" color="red"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>开始时间</td>
                    <td>
                        <input type="datetime-local" id="starttime">
                        <span><font id="start"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>结束时间</td>
                    <td>
                        <input type="datetime-local" id="endtime">
                        <span><font id="end"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>考试时长</td>
                    <td>
                        <input type="number" name="testtime" id="testtime" placeholder="考试时长不能为空">
                        <span><font id="time"></font></span>
                    </td>
                </tr>
                <tr>
                    <td>考试班级</td>
                    <td>
                        <span v-for="(g,gindex) in gradelist">
                            <input type="checkbox" checked name="gid" v-model="g.gid" v-bind:value="g.gid">{{g.gname}}
                            阅卷老师：<select v-model="g.tid" :name="gindex">
                                <option v-for="t in teacherlist" v-bind:value="t.tid">
                                    {{t.tname}}
                                </option>
                            </select><br>
                        </span>
                        <input type="button" value="保存阅卷" @click="saveGradeTeacher()">
                        <input type="button" value="重新选择" @click="getGradeAndTeacher()">
                        <span id="yuejuan" style="color:red;"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button" value="保存" onclick="makeTest()">
                    </td>
                    <td>
                        <input type="reset" value="重置">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</center>
</body>
</html>
<script type="text/javascript" src="../../vuejs/vuejs-2.5.16.js"></script>
<script type="text/javascript" src="../../vuejs/axios-0.18.0.js"></script>
<script type="text/javascript" src="../../vuejs/make_test.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
    function panDuanScore(){
        var passscore = $("#passscore").val();
        var totalscore = $("#totalscore").val();
        if(passscore>totalscore){
            document.getElementById("pass").innerHTML="通过分数不能大于总分";
            $("#passscore").focus();
        }else{
            document.getElementById("pass").innerHTML="";
        }
    }
</script>