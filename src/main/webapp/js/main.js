var ProductManager = {
    defaults:{
        url:{
            getProducts: ""
        },
        id:{
            chosenGroupTitle    : "",
            productsTable       : "",
            rightContainerInner : "",
            prevPage            : "",
            currentPage         : "",
            nextPage            : ""
        },
        name:{
            groupName              : "",
            selectedGroupIndicator : ""
        }

    },
    selectedGroupId   : "",
    currentPageIndex  : 1,
    sortColumnName    : "",
    sortDirection     : "asc",

    initialize:function(properties) {
        $.extend(this.defaults, properties);
    },

    getProductsPage:function(groupId, pageIndex, sortColumnName, sortDirection, shouldUpdateGroupTitle) {
        var self = this;
        var data = {
            'groupId':parseInt(groupId),
            'pageIndex':(pageIndex == undefined) ? 1 : parseInt(pageIndex),
            'sortColumnName':(sortColumnName == undefined) ? "" : sortColumnName,
            'sortDirection':(sortDirection == undefined) ? "" : sortDirection};
        if (shouldUpdateGroupTitle == undefined || shouldUpdateGroupTitle == null)
            shouldUpdateGroupTitle = true;

        $.ajax({
            data:data,
            url:self.defaults.url.getProducts,
            dataType: 'json',
            type:'GET',
            async:false,
            cache:false,
            error:function(){
                alert('Error occurred during getting products.');
            },
            success:function(response){
                self.updateCurrentPageVariables(groupId, pageIndex, sortColumnName, sortDirection);
                self.updateSelectedIndicator(groupId);
                self.updateProjectsPage(response,shouldUpdateGroupTitle);
            }
        });
    },

    getNextPage:function() {
        this.getProductsPage(this.selectedGroupId, this.currentPageIndex + 1, this.sortColumnName, this.sortDirection, false);
    },

    getPreviousPage:function() {
        this.getProductsPage(this.selectedGroupId, this.currentPageIndex - 1, this.sortColumnName, this.sortDirection, false);
    },

    sortProductsTable:function(sortColumnName) {
        var sortDirection = 'asc';
        if (sortColumnName != this.sortColumnName)
            sortDirection = 'asc';
        else
            sortDirection = (this.sortDirection == 'asc') ? 'desc' : 'asc';

        this.getProductsPage(this.selectedGroupId, this.currentPageIndex, sortColumnName, sortDirection, false);
    },

    updateCurrentPageVariables:function(groupId, pageIndex, sortColumnName, sortDirection) {
        this.selectedGroupId = groupId;
        this.currentPageIndex = pageIndex;
        this.sortColumnName = sortColumnName;
        this.sortDirection = sortDirection;
    },

    updateSelectedIndicator:function(groupId) {
        this.getSelectedGroupIndicators().css("display", "none");
        this.getSelectedGroupIndicator(groupId).css("display", "inline-block");
    },

    updateProjectsPage:function(response, shouldUpdateGroupTitle) {
        this.clearProductsTable();

        if (shouldUpdateGroupTitle)
            this.updateGroupTitle(this.selectedGroupId);

        this.getRightInnerContainer().removeClass('invisible');
        this.fillProductsTable(response.products);
        this.rebuildPager(this.currentPageIndex, response.hasPreviousPage, response.hasNextPage);
    },

    clearProductsTable:function() {
        var table = this.getProductsTable();
        table.find("tbody tr").remove();
    },

    updateGroupTitle:function(groupId) {
        var chosenGroupTitle = this.getGroupNameById(groupId);
        this.getChosenGroupTitleElem().text(chosenGroupTitle);
    },

    fillProductsTable:function(products) {
        var table = this.getProductsTable();

        //if category doesn't contain products
        if (!products.length) {
            var row = "<tr><td colspan='2'>This group doesn't contain products</td></tr>";
            table.append(row);
            return;
        }

        $.each(products, function(index, product) {
            var rowClass = "even";
            if (index % 2 == 0)
                rowClass = "odd";

            var row = "<tr class='" + rowClass + "'><td>" + product.name + "</td><td>" + product.price + "</td></tr>";
            table.append(row);
        });
    },

    rebuildPager:function(currentPage, hasPreviousPage, hasNextPage) {
        //previous page
        if (hasPreviousPage)
            this.getPreviousPageControl().css("display", "inline-block");
        else
            this.getPreviousPageControl().css("display", "none");

        //current page
        if (hasPreviousPage || hasNextPage) {
            this.getCurrentPageControl().css({"display":"inline","width":"0px"});
            this.getCurrentPageControl().text(currentPage);
        } else
            this.getCurrentPageControl().css("display", "none");

        //next page
        if (hasNextPage)
            this.getNextPageControl().css("display", "inline-block");
        else
            this.getNextPageControl().css("display", "none");

    },

    getById:function(id) {
        return $("#" + id);
    },
    getProductsTable:function() {
        return this.getById(this.defaults.id.productsTable);
    },
    getSelectedGroupIndicator:function(groupId) {
        return this.getById("group-item-selected-" + groupId);
    },
    getSelectedGroupIndicators:function() {
        return  $("span[name='" + this.defaults.name.selectedGroupIndicator + "']");
    },
    getGroupNameById:function(groupId) {
        return this.getById("group-id-" + groupId).find("span[name='" + this.defaults.name.groupName + "']").text();
    },
    getChosenGroupTitleElem:function() {
        return this.getById(this.defaults.id.chosenGroupTitle);
    },
    getRightInnerContainer:function() {
        return this.getById(this.defaults.id.rightContainerInner);
    },
    getPreviousPageControl:function() {
        return this.getById(this.defaults.id.prevPage);
    },
    getCurrentPageControl:function() {
        return this.getById(this.defaults.id.currentPage);
    },
    getNextPageControl:function() {
        return this.getById(this.defaults.id.nextPage);
    }
}