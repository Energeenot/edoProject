<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="ru">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" th:href="@{/css/footer-page.css}">
</head>
<body>
<#macro footer>
    <footer class="footer">
        <footer class="footer-page">
            <div class="container">
                <div class="footer-page_text">Web service создан Energeenot</div>
            </div>
        </footer>
    </footer>
</#macro>
<#--<div th:fragment="footer">-->
<#--    -->
<#--</div>-->
</body>
</html>