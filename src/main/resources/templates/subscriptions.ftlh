<#import "includes/common.ftlh" as p>

<@p.page>
    <h3>${type}</h3>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">${user.username}</li>
        </#list>
    </ul>
</@p.page>