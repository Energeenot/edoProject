<head>
    <title>Личный кабинет</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <h1> Личный кабинет</h1>
    <form action="/personalAccount" method="post">
        <div class="container-personalData">
<#--            <#if user.name??>-->
                ФИО пользователя: <input type="text" name="name" value="<#if user.name??>${user.name}</#if>"><br>
                Эл. почта <input type="email" name="mail" value="<#if user.name??>${user.mail}</#if>"><br>
                № группы <input type="text" name="numberGroup" value="<#if user.numberGroup??>${user.numberGroup}</#if>"><br>
            <#if message??>
                <div style="color: red" class="invalid-feedback">
                    ${message}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Дополнить информацию">
        </div>
    </form>

    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выйти">
    </form>
</@template.template>
