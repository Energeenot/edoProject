<head>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Andika:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/css/registrationStyle.css">
    <title>Регистрация</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <h1>Регистрация</h1>
    <form action="/registration" method="post">
        <label style="font-family: 'Andika', sans-serif;">ФИО пользователя:</label>
        <input type="text" name="name" class="${(nameError??)?string('is-invalid', '')}" style="font-family: 'Andika', sans-serif;" value="<#if user.name??>${user.name}</#if>"><br>
        <#if nameError??>
            <div style="color: red" class = "invalid-feedback">
                ${nameError}
            </div>
        </#if>
<#--        <#if bindingResult.hasFieldErrors("name")??>-->
<#--            <span style="color: red">${bindingResult.getFieldError("name").defaultMessage}</span><br>-->
<#--        </#if>-->
        <label style="font-family: 'Andika', sans-serif;">Эл. почта </label>
        <input name="mail" value="<#if user.mail??>${user.mail}</#if>"><br>
        <#if mailError??>
            <div style="color: red" class="invalid-feedback">
                ${mailError}
            </div>
        </#if>
<#--        <#if bindingResult.hasFieldErrors("mail")??>-->
<#--            <span style="color: red">${bindingResult.getFieldError("mail").defaultMessage}</span><br>-->
<#--        </#if>-->
        <label style="font-family: 'Andika', sans-serif;">Пароль: </label>
         <input type="password" name="password"><br>
        <#if passwordError??>
            <div style="color: red" class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
<#--        <#if bindingResult.hasFieldErrors("password")??>-->
<#--            <span style="color: red">${bindingResult.getFieldError("password").defaultMessage}</span><br>-->
<#--        </#if>-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Зарегистрироваться" style="font-family: 'Andika', sans-serif;">
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