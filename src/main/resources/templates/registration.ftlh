<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <form action="/registration" method="post">
        ФИО пользователя: <input type="text" name="name"><br>
        Эл. почта <input type="email" name="mail"><br>
        Пароль: <input type="password" name="password"><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Зарегистрироваться">
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@template.template>


<#--<div class="container">-->
<#--    <div th:if="${param.error}">-->
<#--        Invalid username and password-->
<#--    </div>-->
<#--    <div th:if="${param.logout}">-->
<#--        You have logged out-->
<#--    </div>-->
<#--    <form th:action="@{/registration}" method="post">-->
<#--        <input type="text" name="mail" placeholder="Для регистрации необходимо ввести почту."><br>-->
<#--        <input type="text" name="password" placeholder="Для регистрации необходимо ввести почту."><br>-->
<#--        <button type="submit">Зарегистрироваться</button>-->
<#--    </form>-->
<#--</div>-->

<#--<div class="input-field" >-->
<#--    <label class="input-mail">-->
<#--        <span class="input__title">Введите почту</span>-->
<#--        <input type="text" id="mail" class="input_mail" name="" placeholder="почта" autofocus="" aria-required="true" aria-invalid="true">-->
<#--    </label>-->
<#--    <div class="help-block"><p class="help-block help-block-error">Для регистрации необходимо ввести почту.</p></div>-->
<#--    <label class="input-password">-->
<#--        <span class="input__title">Введите почту</span>-->
<#--        <input type="text" id="password" class="input_password" name="" placeholder="пароль" autofocus="" aria-required="true" aria-invalid="true">-->
<#--    </label>-->
<#--    <div class="help-block"><p class="help-block help-block-error">Для регистрации необходимо ввести пароль.</p></div>-->
<#--</div>-->