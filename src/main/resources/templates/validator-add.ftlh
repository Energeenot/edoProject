<head>
    <title>Добавление документа</title>
</head>
<body>
<#import "blocks/template.ftlh" as template>
<@template.template user>
    <div class="container">
        <h1>Добавление документов</h1>

        <form action="/validator-add" method="post" enctype="multipart/form-data">
            <input type="file" name="file" multiple />
            Эл. почта преподавателя <input type="email" name="teacherMail"><br>
            <#if message??>
                <div style="color: red" class="invalid-feedback" >
                    ${message}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Upload File" />
        </form>
    </div>
</@template.template>
<script>
    document.querySelector('form').addEventListener('submit', function(event) {
        var fileInput = document.querySelector('input[type="file"]');
        if (fileInput.files.length === 0) {
            alert('Пожалуйста, выберите файлы для загрузки.');
            event.preventDefault(); // Предотвращаем отправку формы, так как файлы не выбраны
        }
    });
</script>