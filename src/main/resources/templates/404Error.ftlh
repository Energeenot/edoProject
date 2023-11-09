<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Ошибка 404 - Страница не найдена</title>
<!--    <link rel="stylesheet" href="/css/error.css"> &lt;!&ndash; Подключите стили, если необходимо &ndash;&gt;-->
</head>
<body>
<div class="container">
    <h1>Ошибка 404</h1>
    <p>Запрашиваемая страница не найдена.</p>
    <p><a th:href="@{errorMessage}">Вернуться на главную страницу</a></p>
</div>
</body>
</html>
