<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добавление документа</title>
    <link rel="stylesheet" type="text/css" href="/static/css/header-page.css" th:href="@{/css/header-page.css}">
    <link rel="stylesheet" type="text/css" href="/static/css/footer-page.css" th:href="@{/css/footer-page.css}">
</head>
<body>
<#import "blocks/header.ftlh" as header>
<@header.header></@header.header>
<div class="container">
    <h1>Добавление документов</h1>

    <form action="/validator-add" method="post" enctype="multipart/form-data">
        <input type="file" name="file" multiple />
        Эл. почта преподавателя <input type="email" name="teacherMail"><br>

        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Upload File" />
    </form>

<#--    <form action="/validator/add" method="post">-->
<!--        надо перенести сюда форму с валидатора-->
<!--        возможно кнопку надо будет убрать-->

<#--        <input type="text" name="title" placeholder="Введите название"><br>-->
<#--        <input type="text" name="isChanged" placeholder="Введите название"><br>-->
<#--        <button type="submit">Добавить</button>-->
<#--    </form>-->
</div>
<#import "blocks/footer.ftlh" as footer>
<@footer.footer></@footer.footer>
</body>
</html>