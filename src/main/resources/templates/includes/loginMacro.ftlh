<#import "security.ftlh" as security>

<#macro login path sign isRegisterForm>
    <form action="${path}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="mb-3 row">
            <label for="inputPassword" class="col-sm-2 col-form-label">User name</label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                       value="<#if user??>${user.username}</#if>"
                       name="username" placeholder="username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">${usernameError}</div>
                </#if>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(passwordError??)?string('is-invalid','')}"
                       name="password" placeholder="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">${passwordError}</div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="mb-3 row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-6">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.email}</#if>"
                           name="email" placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">${emailError}</div>
                    </#if>
                </div>
            </div>
            <div class="mb-3 row">
                <label for="inputPassword" class="col-sm-2 col-form-label">Password confirm</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control ${(passwordConfirmError??)?string('is-invalid','')}"
                           name="passwordConfirm" placeholder="password"/>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">${passwordConfirmError}</div>
                    </#if>
                </div>
            </div>
        </#if>
        <div class="col-auto">
            <#if !isRegisterForm>
                <a href="registration"><span>Sign up now!</span></a>
            </#if>
            <button type="submit" class="btn btn-primary mb-3">${sign}</button>
        </div>
    </form>
</#macro>

<#macro logout >
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">
            <#if security.user??>
                Sign Out
            <#else>
                Sign In
            </#if>
        </button>
    </form>
</#macro>