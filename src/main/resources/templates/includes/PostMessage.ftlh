
<div class="form-group col-sm-6">
    <div class="post-message"><h3>Post message:</h3></div>
    <form method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                   value="<#if message??>${message.text}</#if>"
                   name="text" placeholder="message"/>
            <#if textError??>
                <div class="invalid-feedback">${textError}</div>
            </#if>
        </div>
        <div class="form-group">
            <input type="text" class="form-control ${(tagError??)?string('is-invalid','')}"
                   value="<#if message??>${message.tag}</#if>"
                   name="tag" placeholder="tag"/>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <label class="custom-file-label" for="customFile">Choose file</label>
                <input type="file" class="custom-file-input" id="customFile"
                       name="file"
                       placeholder="choose file"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group">
            <button type="submit" class="btn btn-primary ml-2">Post</button>
        </div>
    </form>
</div>