<#import "includes/common.ftlh" as p>
<#import "includes/pager_macro.ftlh" as pager>

<@p.page>
    <div class="form-row">
        <div class="col-md-6">
            <form method="get" action="/main">
                <input type="text" name="filter" value="${filter?ifExists}" placeholder="Search"/>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>

    <#include "includes/PostMessage.ftlh"/>
    <div class="post-message"><h3>Posted messages:</h3></div>
    <@pager.pager "/main" page "5"/>
    <#include "includes/MessageList.ftlh"/>

</@p.page>