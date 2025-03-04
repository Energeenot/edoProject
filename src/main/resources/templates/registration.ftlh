<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/static/css/registrationStyle.css">
    <title>Регистрация</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <h1>Регистрация</h1>
    <form action="/registration" method="post">
        <label>ФИО пользователя:</label>
        <input type="text" name="fullName" class="${(nameError??)?string('is-invalid', '')}" style="font-family: 'Andika', sans-serif;" value="<#if user.fullName??>${user.fullName}</#if>"><br>
        <#if nameError??>
            <div style="color: red" class = "invalid-feedback">
                ${nameError}
            </div>
        </#if>
        <label>Эл. почта </label>
        <input name="email" value="<#if email??>${email}</#if>"><br>
        <#if mailError??>
            <div style="color: red" class="invalid-feedback">
                ${mailError}
            </div>
        </#if>
        <label>Пароль: </label>
         <input type="password" name="password"><br>
        <#if passwordError??>
            <div style="color: red" class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Зарегистрироваться">
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@template.template>