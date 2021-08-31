<#import "includes/common.ftlh" as p>
<#import "includes/loginMacro.ftlh" as lm>

<@p.page>
    <div class="mb-1">
        <h3>Add new user:</h3>
    </div>
    <@lm.login "registration" "Register" true/>
</@p.page>