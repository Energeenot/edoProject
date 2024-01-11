<html xmlns:th="http://www.thymeleaf.org" lang="ru">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" th:href="@{/css/header-page.css}">
</head>
<body>
<#macro header>
    <header class="header">

        <header class="header-page">
            <div class="container header-page_container">
                <div class="header-page_start">
                    <div class="logo">
                        <img class="logo_img" src="img/common/logo.svg" alt="" width="60" height="60" >
                    </div>
                </div>
                <div class="header-page_end">
                    <nav class="header-page_nav">
                        <ul class="header-page_ul">
                            <li class="header-page_li">
                                <a class="header-page_link" href="/">
                                    <span class="header-page_text">Главная</span>
                                </a>
                            </li>
                            <#if user??>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/validator">  <!-- data-scroll-to = "section-contacts" -->
                                        <span class="header-page_text">Валидатор</span>
                                    </a>
                                </li>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/validator-add"> <!--data-scroll-to = "section-about"> -->
                                        <span class="header-page_text">Новый документ</span>
                                    </a>
                                </li>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/personalAccount">
                                        <span class="header-page_text">Личный кабинет</span>
                                    </a>
                                </li>

                            <#else>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/login"> <!--data-scroll-to = "section-catalog" -->
                                        <span class="header-page_text">Вход</span>
                                    </a>
                                </li>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/registration"> <!--data-scroll-to = "section-about"> -->
                                        <span class="header-page_text">Регистрация</span>
                                    </a>
                                </li>
                            </#if>


                            <!--                            <li class="header-page_li">-->
                            <!--                                <a class="header-page_link" href="#">-->
                            <!--                                    <span class="header-page_text" data-popup="popup-order">Корзина</span>-->
                            <!--                                </a>-->
                            <!--                            </li>-->
                        </ul>
                    </nav>
                </div>
            </div>
        </header>
    </header>
</#macro>
<#--<div th:fragment="header">-->
<#--    -->
<#--</div>-->
</body>
</html>