<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <link rel="stylesheet" type="text/css" href="/static/css/footer-page.css" th:href="@{/css/footer-page.css}"> <!--  -->
    <link rel="stylesheet" type="text/css" href="/static/css/header-page.css" th:href="@{/css/header-page.css}">

</head>
<body>
<#import "blocks/header.ftlh" as header>
<@header.header></@header.header>

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

<#import "blocks/footer.ftlh" as footer>
<@footer.footer></@footer.footer>
</body>
</html>