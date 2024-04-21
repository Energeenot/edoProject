<head>
    <link rel="stylesheet" type="text/css" href="/static/css/resetPasswordStyle.css">
    <meta charset="UTF-8">
    <title>Восстановление пароля</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <form action="/resetPassword" method="post">
        <div>Введите новый парроль</div>
        Пароль: <input type="password" name="password"><br>
<#--        <#if passwordError??>-->
<#--            <div style="color: red" class="invalid-feedback">-->
<#--                ${passwordError}-->
<#--            </div>-->
<#--        </#if>-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Поменять пароль">
    </form>
    <#if message??>
        <h2 style="color: red">${message}</h2>
    </#if>
</@template.template>