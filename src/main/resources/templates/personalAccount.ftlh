<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ичный кабинет</title>
    <link rel="stylesheet" type="text/css" href="/static/css/footer-page.css" th:href="@{/css/footer-page.css}"> <!--  -->
    <link rel="stylesheet" type="text/css" href="/static/css/header-page.css" th:href="@{/css/header-page.css}">

</head>
<body>
<#import "blocks/header.ftlh" as header>
<@header.header></@header.header>
<h1> Личный кабинет</h1>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти">
</form>
<#import "blocks/footer.ftlh" as footer>
<@footer.footer></@footer.footer>
</body>
</html>