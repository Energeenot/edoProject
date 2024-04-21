<head>
    <link rel="stylesheet" type="text/css" href="/static/css/forgotPasswordStyle.css">
    <title>Восстановление пароля</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <form action="/forgotPassword" method="post">
        <div>Введите эл. почту, на неё будет отправлен код, благодаря которому вы сможете восстановить пароль</div>
        Эл. почта: <input name="mail"><br>
        <#if message??>
            <div style="color: red" class="invalid-feedback">
                ${message}
            </div>
            <a href="/registration">Зарегистрироваться</a>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Восстановить пароль">
    </form>
</@template.template>