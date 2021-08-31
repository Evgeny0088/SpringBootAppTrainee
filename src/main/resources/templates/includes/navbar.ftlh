<#import "security.ftlh" as security>
<#import "loginMacro.ftlh" as lm>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Spring Pet Project</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="/">Home</a>
            </li>

            <#if security.isAdmin>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/users">User List</a>
                </li>
            </#if>
            <#if security.user??>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/main?page=0&size=5">Messages</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/users/profile">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/user-messages/${security.currentUserId}">My messages</a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text mr-2"><#if security.user??>${security.username}<#else> Please Sign In</#if></div>
        <@lm.logout />
    </div>
</nav>