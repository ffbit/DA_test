<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Home</title>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css"/>">
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.7.min.js"/>"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        ProductManager.initialize({
            url:{
                getProducts: "<c:url value="/products-page.do"/>"
            },
            id:{
                chosenGroupTitle    : "chosen-group-title",
                productsTable       : "products-table",
                rightContainerInner : "right-container-inner",
                prevPage            : "prev-page",
                currentPage         : "current-page",
                nextPage            : "next-page"
            },
            name:{
                groupName              : "group-name",
                selectedGroupIndicator : "selected-group-indicator"
            }
        })
    })
</script>
</head>

<body style="padding: 0;margin: 0">
<div class="left-container">
    <div class="product-groups-title">
        Groups of products:
    </div>
    <ul class="products-menu">
        <c:forEach var="group" items="${groups}">
            <li class="group-item" id='group-id-<c:url value="${group.id}"/>' onclick='ProductManager.getProductsPage(<c:out value="${group.id}"/>,1)'>
                <span name="selected-group-indicator" id="group-item-selected-<c:url value="${group.id}"/>" class="selected-group-indicator"></span>
                <span name="group-name"><c:out value="${group.name}"/></span><span>&nbsp;(<c:out value="${fn:length(group.products)}"/>)</span>
            </li>
        </c:forEach>
    </ul>
</div>

<div class="right-container">
    <div id="right-container-inner" class="invisible">
        <div class="chosen-group-title">Products for '<span id="chosen-group-title"></span>' group:</div>
        <div class="pager">
            &nbsp;<span class="vertical-align-helper"></span><span id="prev-page" class="prev-page" onclick="ProductManager.getPreviousPage()"></span>
            <span id="current-page" class="current-page"/></span>
            <span id="next-page" class="next-page" onclick="ProductManager.getNextPage()"/></span>
        </div>
        <table cellpadding="0" cellspacing="0" class="products-table" id="products-table">
            <thead>
            <tr>
                <th style="width: 50%"><span class="sortable-column" onclick="ProductManager.sortProductsTable('name')">Name</span></th>
                <th style="width: 50%"><span class="sortable-column" onclick="ProductManager.sortProductsTable('price')">Price</span></th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>
</body>
</html>