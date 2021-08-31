<#import "includes/common.ftlh" as p>
<#import "includes/pager_macro.ftlh" as pager>

<@p.page>
    <div class="post-message"><h3><strong>${userChannel.username}</strong></h3></div>
    <#if !iscurrentuser>
        <#if ifSubscriber>
            <a class="btn btn-danger" href="/users/unsubscribe/${userChannel.id}">Unsubscribe</a>
        <#else>
            <a class="btn btn-success" href="/users/subscribe/${userChannel.id}">Subscribe</a>
        </#if>
    </#if>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card-body">
                    <div class="card">
                        <div class="card-title">Subscriptions</div>
                        <h3 class="card-text">
                            <a href="/users/subscriptions/${userChannel.id}">${SubscriptionsCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscribers</div>
                        <h3 class="card-text">
                            <a href="/users/subscribers/${userChannel.id}">${SubscribersCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="post-message"><h3>Posted messages:</h3></div>
    <@pager.pager url page "5"/>
    <#include "includes/MessageList.ftlh">

</@p.page>