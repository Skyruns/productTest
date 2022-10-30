<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <script src="jquery.js"></script>
</head>
<body>
    <div id="login">
        <div id="msg" style="color:#f00"></div>
<%--        <form action="<%=request.getContextPath()%>/Login" method="post">--%>
            <div>
                <label for="username">用户名：</label>
                <input id="username" type="text" name="username">
            </div>
            <div>
                <label for="password">密码：</label>
                <input id="password" type="password" name="password">
            </div>
            <div>
                <button onclick="login()">登录</button>
                <input type="reset">
            </div>
<%--        </form>--%>
    </div>
    <script>
        function login(){
            var root = "<%=request.getContextPath()%>"
            var flag = false;
            var msg = $("#msg")

            $.ajax({
                url:root+"/Login",    //请求的url地址
                dataType:"json",   //返回格式为json
                async:false,//请求是否异步，默认为异步true，这也是ajax重要特性
                data:{
                    username:$("#username").val(),
                    password:$("#password").val()
                },    //参数值
                type:"POST",   //请求方式

                success:function(req){
                    //请求成功时处理
                    console.log("请求成功时处理");
                    console.log(req);
                    if (req.data.status == 200){
                        flag = true;
                    }
                },
                complete:function(){
                    //请求完成的处理
                    console.log("请求完成的处理");
                    if (flag){
                        msg.text("登录成功,2秒后跳转")
                        setTimeout(()=>{
                            location.href = "./manage/main.jsp";
                        },2000)
                    }else{
                        msg.text("登录失败")
                        setTimeout(()=>{
                            setTimeout(()=>{
                                msg.text("")
                            },2000)
                        },2000)
                    }
                },
                error:function(){
                    //请求出错处理
                    console.log("请求出错");
                    falg = false;
                    // msg.text("登录失败")
                    // setTimeout(()=>{
                    //     msg.text("")
                    // },2000)
                }
            });
        }
    </script>
</body>
</html>