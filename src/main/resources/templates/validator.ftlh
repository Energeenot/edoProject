<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Валидатор</title>
    <link rel="stylesheet" type="text/css" href="/static/css/validator.css" th:href="@{/css/validator.css}">
    <link rel="stylesheet" type="text/css" href="/static/css/header-page.css" th:href="@{/css/header-page.css}">
    <link rel="stylesheet" type="text/css" href="/static/css/footer-page.css" th:href="@{/css/footer-page.css}">
<#-- надо настроить так же как настраивал до этого css
<script type="text/javascript" th:src="@{/static/js/script.js}" src="/js/script.js"></script>-->
<#--    <script type="text/javascript" th:src="@{/static/js/jquery-3.3.1.min.js}" src="/js/jquery-3.3.1.min.js"></script>-->
<!--    <script type="text/javascript" src="../static/js/jquery-3.3.1.min.js"></script>-->
<!--    <script type="text/javascript" src="../static/js/script.js"></script>-->
</head>
<body>
<#--<header th:insert="~{blocks/header :: header}"></header>-->
<#import "blocks/header.ftlh" as header>
<@header.header></@header.header>

<div class="container-validator">
    <h1>Валидатор</h1>
    <form action="/validator" method="post">
        <span class="input__title">Введите код</span>
        <input type="text" id="kod" class="input_kod" name="uniqueGroupCode" placeholder="kod" autofocus="" aria-required="true" aria-invalid="true">
        <div class="help-block"><p class="help-block help-block-error">Для получения документов необходимо ввести код.</p></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="submit">Найти</button>
    </form>

<!--    код для получения данных-->
<#--<!--    <div th:each="element :${posts}" class="doc">&ndash;&gt;-->
<#--<!--        <h3 th:text="${element.title}"/>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <p th:text=""></p>&ndash;&gt;&ndash;&gt;-->
<#--<!--        <a th:href="'/validator/' + ${element.id}">Детальнее</a>&ndash;&gt;-->
<!--    </div>-->

</div>
<#--<form id="upload-container" method="POST" action="send.php">-->
<#--    <img id="upload-image" src="/images/upload.svg" th:href="@{/images/upload.svg}">-->
<#--    <div>-->
<#--        <input id="file-input" type="file" name="file" multiple>-->
<#--        <label for="file-input">Выберите файл</label>-->
<#--        <span>или перетащите его сюда</span>-->
<#--    </div>-->
<#--</form>-->
<#--<footer th:insert="~{blocks/footer :: footer}"></footer>-->
<#import "blocks/footer.ftlh" as footer>
<@footer.footer></@footer.footer>
</body>
</html>