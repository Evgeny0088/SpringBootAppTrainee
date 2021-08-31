<#import "../includes/common.ftlh" as p>

<@p.page>
    <div class="alert alert-danger col-sm-6" role="alert">
        ${error_message}
    </div>
    <di>
        <a href="/main">
            <span>Back to main page</span>
        </a>
    </di>
</@p.page>