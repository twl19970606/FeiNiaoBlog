<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--[if IE 8]>
<html xmlns="http://www.w3.org/1999/xhtml" class="ie8" lang="zh-CN">
<![endif]-->
<!--[if !(IE 8) ]><!-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<!--<![endif]-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${options.optionSiteTitle} &lsaquo; 登录</title>
    <link rel="stylesheet" href="/plugin/font-awesome/css/font-awesome.min.css">
    <link rel="shortcut icon" href="/img/timg.jpg">
    <link rel='stylesheet' id='dashicons-css'  href='/plugin/login/dashicons.min.css' type='text/css' media='all' />
    <link rel='stylesheet' id='buttons-css'  href='/plugin/login/buttons.min.css' type='text/css' media='all' />
    <link rel='stylesheet' id='forms-css'  href='/plugin/login/forms.min.css' type='text/css' media='all' />
    <link rel='stylesheet' id='l10n-css'  href='/plugin/login/l10n.min.css' type='text/css' media='all' />
    <link rel='stylesheet' id='login-css'  href='/plugin/login/login.min.css' type='text/css' media='all' />
    <style type="text/css">
        body{
            font-family: "Microsoft YaHei", Helvetica, Arial, Lucida Grande, Tahoma, sans-serif;
            background: url(http://localhost:8080/img/back.jpg);
            width:100%;
            height:100%;
        }
        .login h1 a {
            background-size: 220px 50px;
            width: 220px;
            height: 50px;
            padding: 0;
            margin: 0 auto 1em;
        }
        .login form {
            background: #fff;
            background: rgba(255, 255, 255, 0.6);
            border-radius: 2px;
            border: 1px solid #fff;
        }
        .login label {
            color: #000;
            font-weight: bold;
        }

        #backtoblog a, #nav a {
            color: #000000;
        }

    </style><meta name='robots' content='noindex,follow' />
    <meta name="viewport" content="width=device-width" />
    <style>
        body {
            background-repeat: no-repeat;
            background-size: 100% 100%;
            background-attachment: fixed;
        }
    </style>
</head>
<body class="login login-action-login wp-core-ui  locale-zh-cn">
<div id="login">
    <h1><a href="/" title="欢迎您光临本站！" tabindex="-1">${options.optionSiteTitle}</a></h1>
    <form name="loginForm" id="loginForm"  method="post">
        <p>
            <label for="reader_email">电子邮箱：<br />
                <input type="text" name="email" id="reader_email" class="input" size="20" required/></label>
        </p>
        <p>
            <label for="reader_name">用户名：<br />
                <input type="text" name="name" id="reader_name" class="input" size="20" required/></label>
        </p>
        <p>
            <label for="reader_pass">密码：<br />
                <input type="password" name="password" id="reader_pass" class="input" size="20" required/>
            </label>
        </p>
        <p>
            <label for="reader_repass">确认密码：<br />
                <input type="password" name="repassword" id="reader_repass" class="input" size="20" required/>
            </label>
        </p>
        <%--<p>
            <label for="code">请输入验证码：<br />
                <input type="text" name="code" id="code" class="input" size="20" required/>
            </label>
            <img id="imgObj" alt="验证码"src="/getCode"><a href="#" onclick="changeImg()">换一张</a>
        </p>--%>
        <p class="submit clearfix">
            <input type="button" name="wp-submit" id="submit-btn" class="button button-primary button-large" value="注册" />
        </p>
        <p>
            <a href="/reader/login" style="text-decoration:none;">立即登录</a>
        </p>
    </form>

    <p id="backtoblog"><a href="/">&larr; 返回飞鸟博客</a></p>

</div>


<div class="clear"></div>

<script src="/js/jquery.min.js"></script>
<script type="text/javascript">


    <%--注册验证--%>
    $("#submit-btn").click(function () {
        var email = $("#reader_email").val();
        var password = $("#reader_pass").val();
        var name = $("#reader_name").val();
        var rePassword = $("#reader_repass").val();

        if(email=="") {
            alert("电子邮箱不可为空!");
            $("#reader_email").focus();
        }
        else if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            alert("邮箱格式不正确，请重新输入!");
            $("#reader_email").focus();
        }
        else if(name==""){
            alert("用户名不可为空!");
            $("#reader_name").focus();
        }else if(password==""){
            alert("密码不可为空！");
            $("#reader_pass").focus();
        }else if(password!=rePassword){
            alert("前后密码不一致,请重新输入!");
            $("#reader_pass").focus();
        }
        else {
            $.ajax({
                async: false,//同步，待请求完毕后再执行后面的代码
                type: "POST",
                url: '/reader/registerVerify',
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: $("#loginForm").serialize(),
                dataType: "json",
                success: function (data) {
                    if(data.success==false) {
                        alert(data.message);
                    } else {
                        alert("注册成功，请登录!");
                        window.location.href="/reader/login";

            }
        },
                error: function () {
                    alert("数据获取失败");
                }
            })
        }
    })

</script>
</body>
</html>

