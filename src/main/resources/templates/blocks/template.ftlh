<#macro template user>
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
<#--                            <#if user.isStudent()>-->
<#--                                <li class="header-page_li">-->
<#--                                    <a class="header-page_link" href="/validator-add">-->
<#--                                        <!--data-scroll-to = "section-about"> &ndash;&gt;-->
<#--                                        <span class="header-page_text">Новый документ</span>-->
<#--                                    </a>-->
<#--                                </li>-->
<#--                            </#if>-->
                            <#if user.isTeacher()>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/validator">
                                        <!-- data-scroll-to = "section-contacts" -->
                                        <span class="header-page_text">Валидатор</span>
                                    </a>
                                </li>
                            </#if>
                            <#if user.isStudent() || user.isTeacher()>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/personalAccount">
                                        <span class="header-page_text">Личный кабинет</span>
                                    </a>
                                </li>
                            <#else>

                                <li class="header-page_li">
                                    <a class="header-page_link" href="/login">
                                        <!--data-scroll-to = "section-catalog" -->
                                        <span class="header-page_text">Вход</span>
                                    </a>
                                </li>
                                <li class="header-page_li">
                                    <a class="header-page_link" href="/registration">
                                        <!--data-scroll-to = "section-about"> -->
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

    <!-- начало футера -->

    <div class="Container">
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
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var taskRows = document.querySelectorAll('.task-row');
            taskRows.forEach(function(row) {
                // Проверяем, была ли уже создана кнопка для данной строки
                var buttonCreated = false;

                row.addEventListener('click', function() {
                    // Если кнопка ещё не создана, то создаём её и устанавливаем флаг
                    if (!buttonCreated) {
                        var taskId = this.getAttribute('data-task-id');
                        var executeButton = document.createElement('button');
                        executeButton.textContent = 'Выполнить задачу';
                        executeButton.addEventListener('click', function() {
                            window.location.href = '/validator-add?taskId=' + taskId;
                        });

                        // Вставляем кнопку после строки задачи
                        this.insertAdjacentElement('afterend', executeButton);

                        buttonCreated = true; // Устанавливаем флаг, что кнопка создана
                    }
                });

                // Добавляем класс к строке, чтобы пометить её как обработанную
                row.classList.add('task-row-processed');
            });
        });

        document.addEventListener('DOMContentLoaded', function() {
            var taskRows = document.querySelectorAll('.teacher-task-row');
            taskRows.forEach(function(row) {
                // Проверяем, была ли уже создана кнопка для данной строки
                var buttonCreated = false;

                row.addEventListener('click', function() {
                    // Если кнопка ещё не создана, то создаём её и устанавливаем флаг
                    if (!buttonCreated) {
                        var taskId = this.getAttribute('data-task-id');
                        var buttonContainer = document.createElement('div');
                        buttonContainer.classList.add('button-container');

                        var executeForm = document.createElement('form');
                        executeForm.id = 'executeForm';
                        executeForm.action = '/personalAccount/execute';
                        executeForm.method = 'post';

                        var csrfInput = document.createElement('input');
                        csrfInput.type = 'hidden';
                        csrfInput.name = '_csrf';
                        csrfInput.value = '${_csrf.token}';

                        var executeButton = document.createElement('button');
                        executeButton.textContent = 'Отметить выполненной';
                        executeForm.appendChild(csrfInput);
                        executeForm.appendChild(executeButton);
                        buttonContainer.appendChild(executeForm);

                        var taskIdField = document.createElement('input');
                        taskIdField.type = 'hidden';
                        taskIdField.name = 'executeTaskId';
                        taskIdField.value = taskId;
                        executeForm.appendChild(taskIdField);


                        var deleteForm = document.createElement('form');
                        deleteForm.id = 'deleteForm';
                        deleteForm.action = '/personalAccount/delete';
                        deleteForm.method = 'post';

                        var csrfInputDelete = document.createElement('input');
                        csrfInputDelete.type = 'hidden';
                        csrfInputDelete.name = '_csrf';
                        csrfInputDelete.value = '${_csrf.token}';

                        var deleteButton = document.createElement('button');
                        deleteButton.textContent = 'Удалить задачу';
                        deleteForm.appendChild(csrfInputDelete);
                        deleteForm.appendChild(deleteButton);
                        buttonContainer.appendChild(deleteForm);

                        var feedbackForm = document.createElement('form');
                        feedbackForm.id = 'feedbackForm';
                        feedbackForm.action = '/personalAccount/feedback';
                        feedbackForm.method = 'post';

                        var feedbackInput = document.createElement('input');
                        feedbackInput.name = 'feedbackInput';

                        var feedbackButton = document.createElement('button');
                        feedbackButton.textContent = 'Отправить отзыв о документах';

                        var taskIdFeedback = document.createElement('input');
                        taskIdFeedback.type = 'hidden';
                        taskIdFeedback.name = 'feedbackTaskId';
                        taskIdFeedback.value = taskId;

                        var csrfFeedback = document.createElement('input');
                        csrfFeedback.type = 'hidden';
                        csrfFeedback.name = '_csrf';
                        csrfFeedback.value = '${_csrf.token}';

                        feedbackForm.appendChild(csrfFeedback);
                        feedbackForm.appendChild(taskIdFeedback);
                        feedbackForm.appendChild(feedbackInput);
                        feedbackForm.appendChild(feedbackButton);
                        buttonContainer.appendChild(feedbackForm);

                        var taskIdFieldDelete = document.createElement('input');
                        taskIdFieldDelete.type = 'hidden';
                        taskIdFieldDelete.name = 'deleteTaskId';
                        taskIdFieldDelete.value = taskId;
                        deleteForm.appendChild(taskIdFieldDelete);

                        this.insertAdjacentElement('afterend', buttonContainer);
                        buttonCreated = true; // Устанавливаем флаг, что кнопка создана
                    }
                });

                // Добавляем класс к строке, чтобы пометить её как обработанную
                row.classList.add('task-row-processed');
            });
        });


        // document.addEventListener('DOMContentLoaded', function() {
        //     var taskRows = document.querySelectorAll('.teacher-task-row');
        //     taskRows.forEach(function(row) {
        //         // Проверяем, была ли уже создана кнопка для данной строки
        //         var buttonCreated = false;
        //
        //         row.addEventListener('click', function() {
        //             // Если кнопка ещё не создана, то создаём её и устанавливаем флаг
        //             if (!buttonCreated) {
        //                 var taskId = this.getAttribute('data-task-id');
        //                 var buttonContainer = document.createElement('div');
        //                 buttonContainer.classList.add('button-container');
        //                 var executeButton = document.createElement('button');
        //                 executeButton.textContent = 'Отметить выполненной';
        //                 // executeButton.addEventListener('click', function() {
        //                 //     window.location.href = '/personalAccount?taskId=' + taskId;
        //                 // });
        //                 var deleteButton = document.createElement('button');
        //                 deleteButton.textContent = 'Удалить задачу';
        //
        //                 // Вставляем кнопку после строки задачи
        //                 // this.insertAdjacentElement('afterend', executeButton);
        //                 // this.insertAdjacentElement('afterend', deleteButton);
        //                 buttonContainer.appendChild(executeButton);
        //                 buttonContainer.appendChild(deleteButton);
        //                 this.insertAdjacentElement('afterend', buttonContainer);
        //                 buttonCreated = true; // Устанавливаем флаг, что кнопка создана
        //             }
        //         });
        //
        //         // Добавляем класс к строке, чтобы пометить её как обработанную
        //         row.classList.add('task-row-processed');
        //     });
        // });
    </script>
    </body>
    </html>
</#macro>