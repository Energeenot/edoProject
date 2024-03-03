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
<#--            <a href="/registration">Зарегистрироваться</a>-->
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Восстановить пароль">
        <#--        <#if checkCode??>-->
        <#--            Введите код из письма<input name="resetPasswordCode"><br>-->
        <#--            <input type="hidden" name="_csrf" value="${_csrf.token}">-->
        <#--            <input type="submit" value="Проверить код">-->
        <#--        </#if>-->
    </form>
</@template.template>