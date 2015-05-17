var filterController = {};

filterController.keywords = utilService.getUrlVar('keywords');
filterController.filters = utilService.getUrlVar('filters');
filterController.order = utilService.getUrlVar('order');
filterController.page = utilService.getUrlVar('page');
filterController.data = {};
filterController.pageItem = {};

filterController.isDynamic = false
filterController.pathFilters = utilService.urlToMap(filterController.filters);

if (filterController.keywords || filterController.filters || filterController.order || filterController.page){
  filterController.isDynamic = true;
}

if (filterController.isDynamic){

    $('#filterStatic').hide();
    $('#filterLoading').show();

    $pageInfo.stlPageContents = '<stl:pageContents pageNum="9" scope="All"></stl:pageContents></stl:pageContents>';

    var postParams = {keywords: filterController.keywords, filters: filterController.filters, order: filterController.order, page: filterController.page, stlPageContents: $pageInfo.stlPageContents};

    filterService.getResults(postParams, function (data) {
      filterController.data = data;
      filterController.pageItem = data.pageItem;

      $('#filterLoading').hide();
      utilService.render('filterController', filterController);
    });
}

filterController.isFilter = function(filterID, itemID){
  filterID = filterID + '';
  itemID = itemID + '';
  
  if (itemID == '0'){
    return filterController.pathFilters[filterID] == '0';
  }else{
    return filterController.pathFilters[filterID] == itemID;
  }
};

filterController.isOrder = function(order){
  return filterController.data.order == order;
};

filterController.getUrl = function(filterID, itemID, order, page){
  filterMap = utilService.clone(filterController.pathFilters);
  filterMap[filterID] = itemID;
  if (!order) order = filterController.order;
  if (!page) page = filterController.page;

  urlMap = {};
  if (filterController.keywords){
    urlMap['keywords'] = filterController.keywords;
  }
  if (filterMap){
    urlMap['filters'] = utilService.mapToUrl(filterMap);
  }
  if (order){
    urlMap['order'] = order;
  }
  if (page){
    urlMap['page'] = page;
  }

  return "?" + utilService.mapToUrl(urlMap);
};

filterController.redirect = function(filterID, itemID, order, page){
  var url = filterController.getUrl(filterID, itemID, order, page);
  location.href = url;
};