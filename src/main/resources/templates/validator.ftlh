<head>
    <title>Валидатор</title>
<#--    <link rel="stylesheet" type="text/css" href="/static/css/validator.css" th:href="@{/css/validator.css}">-->
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
<div class="container-validator">
    <h1>Валидатор</h1>
    <form action="/validator" method="post" id="validator">
        <span class="input__title">Введите код</span>
        <input type="text" id="kod" class="input_kod" name="uniqueGroupCode" placeholder="kod" autofocus="" aria-required="true" aria-invalid="true">
        <div class="help-block"><p class="help-block help-block-error">Для получения документов необходимо ввести код.</p></div>
        <#if message??>
            <div style="color: red" class="invalid-feedback">
                ${message}
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="submit">Найти</button>
    </form>
</div>
</@template.template>



<!--    код для получения данных-->
<#--<!--    <div th:each="element :${posts}" class="doc">&ndash;&gt;-->
<#--<!--        <h3 th:text="${element.title}"/>&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;        <p th:text=""></p>&ndash;&gt;&ndash;&gt;-->
<#--<!--        <a th:href="'/validator/' + ${element.id}">Детальнее</a>&ndash;&gt;-->
<!--    </div>-->

<#--<form id="upload-container" method="POST" action="send.php">-->
<#--    <img id="upload-image" src="/images/upload.svg" th:href="@{/images/upload.svg}">-->
<#--    <div>-->
<#--        <input id="file-input" type="file" name="file" multiple>-->
<#--        <label for="file-input">Выберите файл</label>-->
<#--        <span>или перетащите его сюда</span>-->
<#--    </div>-->
<#--</form>-->
<#--<footer th:insert="~{blocks/footer :: footer}"></footer>-->
