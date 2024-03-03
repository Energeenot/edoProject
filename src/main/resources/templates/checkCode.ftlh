<head>
    <title>Восстановление пароля</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <form action="/checkCode" method="post">
        <div>Введите код, отправленный вам на почту</div>
        Код: <input name="code"><br>
        <#if message??>
            <div style="color: red" class="invalid-feedback">
                ${message}
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Восстановить пароль">
    </form>
</@template.template>