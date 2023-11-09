<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добавление документа</title>
    <link rel="stylesheet" href="/css/header-page.css" th:href="@{/css/header-page.css}">
    <link rel="stylesheet" href="/css/header-page.css" th:href="@{/css/footer-page.css}">
</head>
<body>
<header th:insert="blocks/header ::header"></header>
<div class="container">
    <h1>Добавление документов</h1>
    <form action="/validator/add" method="post">
<!--        надо перенести сюда форму с валидатора-->
<!--        возможно кнопку надо будет убрать-->
        <input type="text" name="title" placeholder="Введите название"><br>
        <input type="text" name="isChanged" placeholder="Введите название"><br>
        <button type="submit">Добавить</button>
    </form>
</div>
<footer th:insert="blocks/footer ::footer"></footer>
</body>
</html>