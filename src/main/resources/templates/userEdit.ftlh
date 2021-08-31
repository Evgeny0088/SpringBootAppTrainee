<#import "includes/common.ftlh" as p>

<@p.page>
User editor:
<form action="/users" method="post">
    <label>Username<input type="text" name="username" value="${user.username}"></label>
        <br>
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}"${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" value="${user.id}" name="user_id">
    <button type="submit">Save</button>

</form>
</@p.page>