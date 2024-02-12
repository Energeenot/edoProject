<#macro template>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="/static/css/style.css">
        <title>Just cause</title>
    </head>
    <body>
    <header class="header">

        <header class="header-page">
            <div class="container header-page_container">
                <div class="header-page_start">
<#--                    <div class="logo">-->
<#--                        <img class="logo_img" src="img/common/logo.svg" alt="" width="60" height="60">-->
<#--                    </div>-->
                </div>
                <div class="header-page_end">
                    <nav class="header-page_nav">
                        <ul class="header-page_ul">
                            <li class="header-page_li">
                                <a class="header-page_link" href="/">
                                    <span class="header-page_text">Главная</span>
                                </a>
                            </li>
                            <li class="header-page_li">
                                <a class="header-page_link" href="/validator">
                                    <!-- data-scroll-to = "section-contacts" -->
                                    <span class="header-page_text">Валидатор</span>
                                </a>
                            </li>
                            <li class="header-page_li">
                                <a class="header-page_link" href="/validator-add">
                                    <!--data-scroll-to = "section-about"> -->
                                    <span class="header-page_text">Новый документ</span>
                                </a>
                            </li>
                            <li class="header-page_li">
                                <a class="header-page_link" href="/personalAccount">
                                    <span class="header-page_text">Личный кабинет</span>
                                </a>
                            </li>

                            <li class="header-page_li">
                                <a class="header-page_link" href="/login"> <!--data-scroll-to = "section-catalog" -->
                                    <span class="header-page_text">Вход</span>
                                </a>
                            </li>
                            <li class="header-page_li">
                                <a class="header-page_link" href="/registration">
                                    <!--data-scroll-to = "section-about"> -->
                                    <span class="header-page_text">Регистрация</span>
                                </a>
                            </li>


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

    <!-- начало футера -->

    <div class="Conteiner">
        <div class="content">
            <#nested/>
        </div>
        <div class="footer">
            <div class="footer-page">
                <div class="container">
                    <div class="footer-page_text">Web service создан Energeenot</div>
                </div>
            </div>
        </div>
    </div>
    </body>
    </html>
</#macro>