<#macro pager url page size>

    <#assign
    current_page = page.getNumber()+1
    first_page = 1
    total = page.getTotalPages()+1
    >

    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1
        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])
        pagination = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
        <#else>
            <#assign
            pagination = first_page..total
            >
    </#if>

    <div>
        <ul class="pagination">
            <#list pagination as p>
                <#if (p-1) == page.getNumber()>
                    <li class="page-item active" aria-current="page">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#elseif p == -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item" aria-current="page">
                        <a class="page-link" href="${url}?page=${p-1}&size=${size}" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</#macro>
