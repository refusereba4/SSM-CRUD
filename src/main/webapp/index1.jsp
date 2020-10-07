<%--
  Created by IntelliJ IDEA.
  User: JasonFan
  Date: 2020/7/11
  Time: 下午5:34
  To change this template use File | Settings | File Templates.
--%>
<%--在jsp页面发送查询请求，然后控制器来接收，所以用jsp:forward 指定请求的URI，然后再建一个控制器来接收并处理请求
    就可以了，下面的路径就是转到当前项目下的emps
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:forward page="/emps"></jsp:forward>
<html>
<head>
    <title>Title</title>
    <%--    引入jQuery--%>
    <script type="text/javascript" src="static/js/jquery-1.12.4.min.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<script type="javascript">
    <%--       1.页面加载完以后，直接去发送一个ajax请求，要到分页数据 --%>
    $(function () {
        $.ajax({
            url:"${APP_PATH}/emps",
            data:"pn=1",
            type:"GET",
            success:function (result) {
                console.log(result);
                //    解析并显示员工数据
                //    解析并显示分页信息
            }

        });
    })
    function build_emps_table(result) {

    }
    function build_page_nav(result) {

    }
</script>
</body>
</html>