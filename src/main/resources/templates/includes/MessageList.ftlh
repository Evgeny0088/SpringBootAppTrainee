<#import "security.ftlh" as security>

<#list page.getContent() as message>
    <div class="card mb-3" style="..." id="${message.id}">
        <div class="row g-0">
            <#if message.filename??>
                <div class="col-md-4">
                    <img class="img-fluid rounded-start" src="/img/${message.filename}" alt=""/>
                </div>
            </#if>
            <div class="col-md-6">
                <div class="card-body">
                    <div class="container">
                        <div class="row">
                            <div class="col-6 d-flex justify-content-left text-left">
                                <h5 class="card-title"><a class="col align-self-center" href="/user-messages/${message.author.id}">
                                        ${message.getAuthorName()}</a></h5>
                                <#--likes-->
                                <#if message.author.id==security.currentUserId>
                            <a class="card-title" href="#"/>
                            <#else>
                                <a class="card-title" href="/message/${message.id}/like">
                                    </#if>
                                    <#if message.meLiked>
                                        <i class="fas fa-heart"></i>
                                    <#else>
                                        <i class="far fa-heart"></i>
                                    </#if>
                                </a>
                                <p class="card-text">${message.likes}</p>
                                <#--dislikes-->
                                <#if message.author.id==security.currentUserId>
                                <a class="card-title" href="#"/>
                                <#else>
                                <a class="card-title" href="/message/${message.id}/dislike">
                                    </#if>
                                    <#if message.medisliked>
                                        <i class="fas fa-thumbs-down"></i>
                                    <#else>
                                        <i class="far fa-thumbs-down"></i>
                                    </#if>
                                </a>
                                <p class="card-text">${message.dislikes}</p>
                            </div>
                        </div>
                    </div>
                    <p class="message-text">${message.text}</p>
                    <#if message.author.id==security.currentUserId>
                        <a class="btn btn-primary mb-3" href="/user-messages/${message.author.id}/${message.id}">Edit</a>
                        <a class="btn btn-danger mb-3" href="/user-messages/delete/${message.id}">Delete</a>
                    </#if>
                    <p class="card-text"><small class="text-muted">#${message.tag}</small></p>
                </div>
            </div>
        </div>
    </div>
<#else>
    No messages
</#list>
