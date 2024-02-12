<head>
    <title>Авторизация</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <form action="/login" method="post">
        Эл. почта: <input type="email" name="username"><br>
        Пароль: <input type="password" name="password"><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Вход">
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
    <a href="/registration">Зарегистрироваться</a>
</@template.template>



