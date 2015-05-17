function thumbnail2(){
    //设置和获取一些变量
  var thumbnail2 = {
    imgWidthIncrease : 100, /* 增加图像像素（变焦） */
    imgHeightIncrease : 169, /* 增加图像像素（变焦） */
    effectDuration : 400, /* 效果的持续时间（变焦和标题） */
    /* 
    获取的图像的宽度和高度。要使用这些
    2件事:
    列表项大小相同
    得到的图像缩放后恢复正常
    */
    imgWidth : 211, 
    imgHeight : 356
  };

  //列表项相同的大小作为图像
  $('.thumbnailWrapper2 .templatePic2').css({ 
    'width' : thumbnail2.imgWidth, 
    'height' : thumbnail2.imgHeight 
  });

  //当鼠标移到列表项...
  $('.thumbnailWrapper2 .templatePic2').hover(function(){
    $(this).find('img').stop().animate({
      /* 变焦效果，提高图像的宽度 */
      width: parseInt(thumbnail2.imgWidth) + thumbnail2.imgWidthIncrease,
      height: parseInt(thumbnail2.imgHeight) + thumbnail2.imgHeightIncrease,
      /* 我们需要改变的左侧和顶部的位置，才能有放大效应，因此我们将它们移动到一个负占据一半的img增加 */
      left: thumbnail2.imgWidthIncrease/2*(-1),
      top: thumbnail2.imgHeightIncrease/2*(-1)
    },{ 
      "duration": thumbnail2.effectDuration,
      "queue": false
    });

    //使用slideDown事件显示的标题
    $(this).find('.caption:not(:animated)').slideDown(thumbnail2.effectDuration);
    
    //当鼠标离开...
  }, function(){
  
    //发现图像和动画...
    $(this).find('img').animate({
      /* 回原来的尺寸（缩小） */
      width: thumbnail2.imgWidth,
      height: thumbnail2.imgHeight,
      /* 左侧和顶部位置恢复正常 */
      left: 0,
      top: 0

    }, thumbnail2.effectDuration);

    //隐藏使用滑块事件的标题
    $(this).find('.caption').slideUp(thumbnail2.effectDuration);

  });
}

$(document).ready(thumbnail2());