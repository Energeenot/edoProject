<head>
    <link rel="stylesheet" type="text/css" href="/static/css/loginStyle.css">
    <title>Авторизация</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <h1>Авторизация</h1>
    <form action="/login" method="post">
<#--        почта должна быть username-->
        Эл. почта: <input name="username" value="<#if user.mail??>${user.mail}</#if>"><br>
        <#if mailError??>
            <div style="color: red" class="invalid-feedback">
                ${mailError}
            </div>
        </#if>
        Пароль: <input type="password" name="password"><br>
        <#if passwordError??>
            <div style="color: red" class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Вход">
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
    <a href="/registration">Зарегистрироваться</a><br>
    <a href="/forgotPassword">Восстановить пароль</a>
</@template.template>



