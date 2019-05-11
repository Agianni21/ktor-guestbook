<#-- @ftlvariable name="messages" type="agiani.List<GuestMessages>" -->
<html>
    <head>
        <link rel="stylesheet" href="/static/styles.css">
    </head>
    <body>
        <form method="post">
            <p>Your name</p>
            <input type="text" name="name">
            <p>Your message</p>
            <textarea col=50 row=10 name="message"></textarea>
            <div>
                <input type="submit">
            </div>
        </form>
        <#list messages as message>
        <div class="message">
            <p class="message_name">${message.name}</p>
            <p class="message_body">${message.message}</p>
        </div>
        </#list>
    </body>
</html>
