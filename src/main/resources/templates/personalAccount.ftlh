<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" type="text/css" href="/static/css/personalStyle.css">
</head>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <h1> Личный кабинет</h1>
    <div class="sidebar">
        <button id="userInfoBtn">Информация о пользователе</button><br>
        <button id="tasksBtn">Задачи</button>
    </div>

    <div id="userInfoContainer" class="container-personalInfo">
        <form action="/personalAccount/supplementInformation" method="post">
            <div class="container-personalData">
                <#--            <#if user.name??>-->
                 <label>ФИО пользователя:
                    <input type="text" name="name" value="<#if user.name??>${user.name}</#if>">
                </label><br>
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
    </div>

    <div id="tasksContainer" class="tasksContainer" style="display: none;">
            <a>Задачи</a><br>
            <#if user.isTeacher()>
                <#if tasks??>
                    <div id="showAllTasks">
                        <table border="1">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>Сообщение</th>
                                <th>Имя ученика</th>
                                <th>Стадия выполнения</th>
                                <th>Код для проверки документов</th>
                                <th>Дедлайн</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if numberGroups??>
                                <#list tasks as task>
                                    <tr class="teacher-task-row" data-task-id="${task.id?string}">
                                        <td>${task.id}</td>
                                        <td>${task.message}</td>
                                        <td>${task.user.name}</td>
                                        <td>${task.stage}</td>
                                        <td>
                                            <#if task.files?exists && task.files.uniqueGroupCode?exists>
                                                ${task.files.uniqueGroupCode}
                                            <#else>
                                                Не отправлен
                                            </#if>
                                        </td>
                                        <td>${task.deadline}</td>
                                    </tr>
                                </#list>
                            </#if>

                            </tbody>
                        </table>
                    </div>
                <#else>
                    <a>Нет созданных задач</a>
                </#if>
                <div id="createTaskForm" style="display: none;">
                    <form action="/personalAccount/createTask" method="post">
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}">-->
                        <div class="info">
                            <label>Для того чтобы создать задачу, введите сообщение, выберите группы для которых будет создана задача, выберите дедлайн, и сохраните задачу.</label>
                        </div>
                        <div>
                            <label for="message">Сообщение:</label>
                            <input type="text" id="message" name="message">
                        </div>
                        <div class="custom-select" onclick="toggleOptions()">
                            <span id="selectedOption">Выберите группы: </span><br>
                            <div id="optionsList" class="options-list">
                                <#if numberGroups??>
                                    <#list numberGroups as group>
                                        <div class="option" onclick="toggleSelection(event)">
                                            <input type="checkbox" class="checkbox" value="${group}" id="${group}">
                                            <label for="${group}">${group}</label>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                            <input style="display: none" id="selectedGroupsInput" name="selectedGroups">
                        </div>
                        <div>
                            <label for="deadline">Дедлайн:</label>
                            <input type="date" id="deadline" name="deadline">
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" class="save-button" value="Сохранить задачу">
                    </form>
                </div>
                <button id="createTask">Создать задачу</button>
<#--                <input type="hidden" name="_csrf" value="${_csrf.token}">-->
<#--                <input type="submit" value="Удалить задачу">-->
<#--                <input type="hidden" name="_csrf" value="${_csrf.token}">-->
<#--                <input type="submit" value="Задача выполнена">-->
            <#else>
                <#if tasks??>
                    <a> Существующие задачи</a><br>
                    <div id="showTask">
                        <table border="1">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>Сообщение</th>
                                <th>Имя реподавателя</th>
                                <th>Стадия выполнения</th>
                                <th>Код для проверки документов</th>
                                <th>Дедлайн</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list tasks as task>
                                <tr class="task-row" data-task-id="${task.id?string}">
                                    <td>${task.id}</td>
                                    <td>${task.message}</td>
                                    <td>${task.sender.name}</td>
                                    <td>${task.stage}</td>
                                    <td>
                                        <#if task.files?exists && task.files.uniqueGroupCode?exists>
                                            ${task.files.uniqueGroupCode}
                                        <#else>
                                            Не отправлен
                                        </#if>
                                    </td>
                                    <td>${task.deadline}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                <#else>
                    <a>Нет задач</a>
                </#if>

            </#if>
    </div>


    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" class="exit-button" value="Выйти">
    </form>
</@template.template>
<script>
    document.getElementById("userInfoBtn").addEventListener("click", function() {
        document.getElementById("userInfoContainer").style.display = "block";
        document.getElementById("tasksContainer").style.display = "none";
    });

    document.getElementById("tasksBtn").addEventListener("click", function() {
        document.getElementById("userInfoContainer").style.display = "none";
        document.getElementById("tasksContainer").style.display = "block";
    });
    document.getElementById("createTask").addEventListener("click", function () {
        document.getElementById("createTaskForm").style.display = "block"
    });

    function toggleOptions() {
        var optionsList = document.getElementById("optionsList");
        if (optionsList.style.display === "block") {
            optionsList.style.display = "none";
        } else {
            optionsList.style.display = "block";
        }
    }
// этот метод не удаляет первый элемент
    function toggleSelection(event) {
        var selectedOption = document.getElementById("selectedOption");
        var checkbox = event.currentTarget.querySelector(".checkbox");
        var labelText = event.currentTarget.querySelector("label").textContent;
        var selectedOptions = selectedOption.innerHTML.split(", ");
        var selectedGroupsInput = document.getElementById("selectedGroupsInput"); // Скрытое поле для хранения выбранных элементов
        var index = selectedOptions.indexOf(labelText);
        if (checkbox.checked) {
            if (index === -1) {
                selectedOption.innerHTML += labelText + ", ";
                selectedGroupsInput.value += labelText + ", "; // Добавляем новое значение к уже существующим
            }
        } else {
            if (index !== -1) {
                selectedOptions.splice(index, 1);
                selectedOption.innerHTML = selectedOptions.filter(Boolean).join(", ") + ", ";
                // Обновляем selectedGroupsInput, используя исходные labelText
                var originalLabels = selectedGroupsInput.value.split(", ");
                var indexOfLabel = originalLabels.indexOf(labelText);
                if (indexOfLabel !== -1) {
                    originalLabels.splice(indexOfLabel, 1);
                    selectedGroupsInput.value = originalLabels.join(", ");
                }
            }
        }
        if (selectedOptions.length === 0) {
            selectedGroupsInput.value = "";
        }
    }






</script>
