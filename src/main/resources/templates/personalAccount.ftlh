<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
    <link rel="stylesheet" type="text/css" href="/static/css/footer-page.css" th:href="@{/css/footer-page.css}"> <!--  -->
    <link rel="stylesheet" type="text/css" href="/static/css/header-page.css" th:href="@{/css/header-page.css}">

</head>
<body>
<#import "blocks/header.ftlh" as header>
<@header.header></@header.header>
<h1> Личный кабинет</h1>
<form action="/personalAccount" method="post">
    <div class="container-personalData">
<#--        <h2>${user.name}</h2>-->
<#--        <h2>${user.mail}</h2>-->
        ФИО пользователя: <input type="text" name="name" value="${user.name}"><br>
        Эл. почта <input type="email" name="mail" value="${user.mail}"><br>
        <#if user.numberGroup??>
            № группы <input type="text" name="numberGroup" value="${user.numberGroup}"><br>
            Эл. почта преподавателя <input type="email" name="teacherMail" value="${user.teacherMail}"><br>
        <#else>
            № группы <input type="text" name="numberGroup"><br>
            Эл. почта преподавателя <input type="email" name="teacherMail"><br>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Дополнить информацию">
    </div>
</form>




<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти">
</form>
<#import "blocks/footer.ftlh" as footer>
<@footer.footer></@footer.footer>
</body>
</html>