/*
 *预览图片
 * Imgview
 */
$(document).ready(function(){
    window.$_Imgview = new ImgViewWrap({});
})

function ImgViewWrap(conf){
    this.conf = conf || {};
    this.init = initImgviewFun;
    this.show = showImgviewFun;
    this.hide = hideImgviewFun;
    this.returnimghtml = returnImgHtml;
    this.checkImgExists = checkImgExistsFun;

    this.init();
}

function initImgviewFun(){
    this.imgviewbox = $("#imgViewBox");
    this.closeimgview = $("#closeImgView");
}

/*
* 显示图片:show()
* */
function showImgviewFun(option){
    this.option = option || {};
    this.imgPath = option.imgPath;
    this.imghtml = this.returnimghtml(this.imgPath);
    if($("#imgViewBox").length == 0){
        $("body").append(this.imghtml);
        this.imgViewBox = $("#imgViewBox");
        this.closeimgview = $("#closeImgView");

        this.hide();
    }
}
/*
 * 关闭图片:hide()
 * */
function hideImgviewFun(){
    $("#closeImgView").off("click");
    $("#closeImgView").on("click",function(){
        $("#imgViewBox").remove();
        if($("#bgShadow").length != 0){
            $("#bgShadow").hide();
        }
    });
}

/*
 * 计算图片大小 
 * */
function getImageSizeFun(){
    var imgTri = this.imgViewBox.find("img");
    var imgSizeW = imgTri.width(),
        imgSizeH = imgTri.height();
    if(imgSizeW > 1000){
        imgTri.width(1000);
    }

    var marginT = parseInt(imgTri.height()/2),
        marginL = parseInt(imgTri.width()/2);
    this.imgViewBox.css({"margin-top":-marginT,"margin-left":-marginL})
}

/*
 * 判断图片是否存在 
 * */
function checkImgExistsFun(imgurl){
    var ImgObj = new Image(); //判断图片是否存在  
    ImgObj.src = imgurl;  

    var imgWid = ImgObj.width,
        imgHei = ImgObj.height,
        imgSize = ImgObj.fileSize;
    if (imgSize > 0 || (imgWid > 0 && imgHei > 0)) {  
        return true;
    }else{
        return false;
    }
}

/*
* 返回 html
* path:图片路径
* */
function returnImgHtml(path){
    return [
        '<div class="imgviewbox" id="imgViewBox">',
            '<table cellspecing="0" cellpadding="0">',
               ' <tr>',
                    '<td>',
                        '<div class="sideimg">',
                            '<a href="javascript:void(0);" class="closeimgview" id="closeImgView"></a>',
                            '<img src="'+path+'" alt="图片">',
                        '</div>',
                    '</td>',
                '</tr>',
            '</table>',
        '</div>'
    ].join("")
}