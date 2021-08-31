<#import "includes/common.ftlh" as p>

<@p.page>
    <div class="alert alert-success col-sm-8" role="alert">
        message #${message_id} is deleted!
    </div>
    <di>
        <a href="/main">
            <span>Back to main page</span>
        </a>
    </di>
</@p.page>