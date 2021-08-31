<#import "includes/common.ftlh" as p>

<@p.page>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/users/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@p.page>