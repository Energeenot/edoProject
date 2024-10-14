<head>
    <link rel="stylesheet" type="text/css" href="/static/css/homeStyle.css">
<!--    <meta name="keywords" content="эдо, электронный документооборот, пэп, электронная подпись, смс подпись, sms подпись, мобильная подпись">-->

    <title>НАЗВАНИЕ – простая электронная подпись по почте</title>
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <div class="section-about">
        <h1 class="banner__title">
            <span class="banner_span">ЭДО "Практика"</span>
        </h1>
        <h2 class="banner__title">Cпециализированный сервис эдо для упрощения сдачи тех практики</h2>
        <p class="banner__descr">Последние годы активно развиваются сервисы электронного документооборота для юридических лиц. Все больше и больше компаний уходят от бумажного документооборота, выбирая ЭДО. Однако в университетах всё остаётся по старому. Этот веб сервис предназначен для того чтобы упростить сдачу тех практики.</p>
    </div>

    <div class="instruction">
        <div class="info_list-block">
            <div class="info_subtitle">Как работать с данным сервисом?</div>
            <ul class="marker">
                <li>Ученик регистрируется на сайте</li>
<#--                <li>вводит вашу почту</li>-->
                <li> выбирает задачу для сдачи</li>
                <li>нажимает сдать задачу</li>
                <li>выбирает документы на проверку</li>
                <li>вам приходит код</li>
                <li>вы заходите на сайт </li>
                <li> на странице валидатор скачиваете документы</li>
                <li>проверяете их</li>
                <li>отправляете ученику обратную связь</li>
            </ul>
        </div>
    </div>
</@template.template>


<#--<footer th:insert="~{blocks/footer :: footer}"></footer>-->
<#--<#import "blocks/footer.ftlh" as footer>-->
<#--<@footer.footer></@footer.footer>-->

