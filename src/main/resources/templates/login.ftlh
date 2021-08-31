<#import "includes/common.ftlh" as p>
<#import "includes/loginMacro.ftlh" as lm>

<@p.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger col-sm-8" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType} col-sm-8" role="alert">
            ${message}
        </div>
    </#if>
    <@lm.login "/login" "Sign in" false/>
</@p.page>