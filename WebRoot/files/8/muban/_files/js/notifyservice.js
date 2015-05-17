var notifyService = {
  successAddCart: function (e) {
      toastr.success('所选商品已添加至购物车，请进入购物车查看');
  },
  successAddFollow: function (e) {
      toastr.success('所选商品已添加至我的收藏');
  },
  successAddInvoice: function (e) {
      toastr.success('新的发票信息已保存成功');
  },
  successCallback: function (e) {
      toastr.success('操作成功');
  },
  errorCallback: function (e) {
      var error = e.data.exceptionMessage;
      if (!error){
          error = e.data.message;
      }
      if (error){
          toastr.error('操作失败：' + error);
      }else{
          toastr.error('操作失败');
      }
  },
  success: function (text) {
      if (!text){
          text = "操作成功";
      }
      toastr.success(text);
  },
  error: function (text) {
      if (!text){
          text = "操作失败";
      }
      toastr.error(text);
  }
};