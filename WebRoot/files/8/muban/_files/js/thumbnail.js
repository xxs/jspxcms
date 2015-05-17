function thumbnail(){
    //设置和获取一些变量
  var thumbnail = {
    imgWidthIncrease : 100, /* 增加图像像素（变焦） */
    imgHeightIncrease : 73, /* 增加图像像素（变焦） */
    effectDuration : 400, /* 效果的持续时间（变焦和标题） */
    /* 
    获取的图像的宽度和高度。要使用这些
    2件事:
    列表项大小相同
    得到的图像缩放后恢复正常
    */
    imgWidth : 315, 
    imgHeight : 230
  };

  //列表项相同的大小作为图像
  $('.thumbnailWrapper .templatePic').css({ 
    'width' : thumbnail.imgWidth, 
    'height' : thumbnail.imgHeight 
  });

  //当鼠标移到列表项...
  $('.thumbnailWrapper .templatePic').hover(function(){
    $(this).find('img').stop().animate({
      /* 变焦效果，提高图像的宽度 */
      width: parseInt(thumbnail.imgWidth) + thumbnail.imgWidthIncrease,
      height: parseInt(thumbnail.imgHeight) + thumbnail.imgHeightIncrease,
      /* 我们需要改变的左侧和顶部的位置，才能有放大效应，因此我们将它们移动到一个负占据一半的img增加 */
      left: thumbnail.imgWidthIncrease/2*(-1),
      top: thumbnail.imgHeightIncrease/2*(-1)
    },{ 
      "duration": thumbnail.effectDuration,
      "queue": false
    });

    //使用slideDown事件显示的标题
    $(this).find('.caption:not(:animated)').slideDown(thumbnail.effectDuration);
    
    //当鼠标离开...
  }, function(){
  
    //发现图像和动画...
    $(this).find('img').animate({
      /* 回原来的尺寸（缩小） */
      width: thumbnail.imgWidth,
      height: thumbnail.imgHeight,
      /* 左侧和顶部位置恢复正常 */
      left: 0,
      top: 0

    }, thumbnail.effectDuration);

    //隐藏使用滑块事件的标题
    $(this).find('.caption').slideUp(thumbnail.effectDuration);

  });
}

$(document).ready(thumbnail());