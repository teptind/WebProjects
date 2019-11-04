<#macro header>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            <@userlink user=user/>
            |
            <a href="#">Logout</a>
        <#else>
            <a href="#">Enter</a>
            |
            <a href="#">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <#assign navMap={"Index": "/index", "Help": "/misc/help"}>
            <#list navMap?keys as key>
                <#if navMap[key] == uri>
                    <li class="underlined">
                <#else>
                    <li>
                </#if>
                    <a href=${navMap[key]}>${key}</a>
                </li>
            </#list>
        </ul>
    </nav>
</header>
</#macro>

<#macro sidebar>
<aside>
    <section>
        <div class="header">
            Pay attention
        </div>
        <div class="body">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
            nulla placeat quam suscipit unde voluptatibus.
        </div>
        <div class="footer">
            <a href="#">View all</a>
        </div>
    </section>
</aside>
</#macro>

<#macro footer>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
</footer>
</#macro>

<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
</head>
<body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
</body>
</html>
</#macro>

<#macro userlink user>
    <a class="${user.color}" href="/user?handle=${user.handle}">${user.handle}</a>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findIndex items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item?index/>
        </#if>
    </#list>
</#function>
