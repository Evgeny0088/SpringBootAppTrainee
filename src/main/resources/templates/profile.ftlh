<#import "includes/common.ftlh" as p>

<@p.page>
    <form action="/users/profile" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="mb-3 row">
            <label for="inputPassword" class="col-sm-2 col-form-label">User name</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="username" placeholder="username"
                value="${username}"/>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="password" placeholder="password"/>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="email" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" class="form-control" name="email" placeholder="some@some.com"
                value="${email!''}"/>
            </div>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3">Save</button>
        </div>
    </form>
</@p.page>