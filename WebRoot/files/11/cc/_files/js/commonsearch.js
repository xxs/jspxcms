$(function(){
    window.commonSearchWrap = new CommonSearchWrap({});
})

/********** common search  **********/
function CommonSearchWrap(){
    this.search = $("#Search"); //搜索
    this.searchCont = $("#searchCont");
    this.searchBtn = $("#searchBtn");
    this.searchClear = $("#searchClear");
    this.searchDownList = $("#searchDownList");
    this.associateWord = $("#associateWord");
    this.searchMoreWord = $("#searchMoreWord");
    this.down_curli_index = 0;

    this.init();
}
CommonSearchWrap.prototype = {
    init : function(){
        this.inputEventFun(); //搜索输入框事件
        this.searchBtnFun(); //搜索
        this.searchClearFun(); //清空搜索
    },

    inputEventFun : function(){
        var that = this;
        var input = that.searchCont;
        input.bind("focus",function(){
            var _this = $(this);
            var val = $.trim(_this.val());
            var initval = _this.attr("initval");
            if(val == "" || _this.val() == initval){
                 _this.val("");

                that.searchClear.fadeOut();
                that.searchDownList.hide();
            }else{
                that.searchClear.fadeIn();
                that.getHotWordFun(val);
                that.searchMoreWord.find("i").text(val);
                that.searchDownList.show();
            }
        }).bind("blur",function(){
            var _this = $(this);
            var initval = _this.attr("initval");
            if(_this.val() == "" || _this.val() == initval){
                 _this.val(initval);
            }
            //that.searchDownList.hide();
        }).bind("keyup",function(event){
            var _this = $(this);
            var val = $.trim(_this.val());
            var initval = _this.attr("initval");

            var event = event || window.event;
            var keyCode = event.keyCode;
            
            if(keyCode == 27 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40){
                return false;
            }
           
            if(val == "" || _this.val() == initval){
                that.searchClear.fadeOut();
                that.searchDownList.hide();
            }else{
                that.searchClear.fadeIn();
                that.getHotWordFun(val);
                that.searchDownList.show();
            }

        }).bind("keydown",function(event){
            var event = event || window.event;
            var keyCode = event.keyCode;

            switch (keyCode) {
                case 13: //Enter
                    event.preventDefault(); 
                    that.searchBtn.trigger("click");
                    break;
                case 38: //Up Arrow
                    event.preventDefault(); 
                    that.keyDownEventFun(-1);
                    break;
                case 40: //Down Arrow
                    event.preventDefault(); 
                    that.keyDownEventFun(1);
                    break;
                case 27: //Esc
                    event.preventDefault(); 
                    that.keyDownEventFun(0);
                    break;
                default:
                   // that.searchCont.focus();
            }
        });
    },
    //键盘事件 -- 选中下拉node
    keyDownEventFun : function(arg){
        var that = this;
        that.searchDownList = $("#searchDownList"); //下拉热词是后添加的，需要在次赋值
        var hot_word_li_len = that.searchDownList.find("li").length;
        if(arg == -1){
            that.down_curli_index = that.searchDownList.find("li.current").index();
            that.down_curli_index --;

            if(that.down_curli_index < 0){
                that.down_curli_index = hot_word_li_len - 1;
            }
            that.selectDownNodeFun();
        }else if(arg == 1){
            that.down_curli_index = that.searchDownList.find("li.current").index();
            that.down_curli_index ++;

            if(that.down_curli_index >= hot_word_li_len){
                that.down_curli_index = 0;
            }
            that.selectDownNodeFun();
        }else{
            that.searchDownList.hide();
        }
    },
    selectDownNodeFun : function(){
        var that = this;
        var down_curli = that.searchDownList.find('li:eq('+ that.down_curli_index +')');
        var hot_word_li_len = that.searchDownList.find("li").length;

        that.searchDownList.find('li').removeClass("current"); 
        down_curli.addClass("current");   
        that.searchCont.val(down_curli.find("a").text());

        if(that.down_curli_index >= (hot_word_li_len - 1)){
            that.searchCont.val(down_curli.find("a i").text());
        }
    },
    //获取搜索内容联想词
    getHotWordFun : function(text){
        var that = this;
        var data = {};
        data.word = text;

        $.ajax({
           type : "POST",
           url : "/jsapi/search_hot_word.html",
           data : data,
           dataType: "json",
           success : function(res){
                //var res = {"res":1,"data":[{"id":"1","word":"cocos studio","short":"c,cs,co","type":"1","weight":"100","update_time":"2015-06-03 09:20:52"}],"info":"success"}; 
                if(res.res == 1){ //搜索成功
                    that.associateWord.html("");
                    that.associateWord.append('<li class="searchmore_dsh"><a id="searchMoreWord" href="javascript:void(0);">搜索更多的 “<i></i>”</a></li>');
                    
                    that.searchMoreWord = $("#searchMoreWord");
                    that.searchMoreWord.find("i").text(text);
                    if(res.data.length < 1){
                        that.associateWord.find(".searchmore_dsh").removeClass("hasborder_dsh");
                    }else{
                        var data = res.data;
                        var list_html = "";
                        for(var i=0; i<data.length; i++){
                            list_html += '<li><a id="'+data[i].id+'">'+data[i].word+'</a></li>';
                        }
                        that.associateWord.prepend(list_html);
                        that.associateWord.find(".searchmore_dsh").addClass("hasborder_dsh");

                        that.associateWordFun();  //联想词搜索
                    }
                }
           }
        });
    },
    //清空搜索
    searchClearFun : function(){
        var that = this;
        that.searchClear.unbind("click");
        that.searchClear.bind("click",function(){
            var _this = $(this);
            var initval = that.search.find("initval").attr("initval");
            that.search.find("input").val(initval);
            that.searchDownList.hide();
            _this.fadeOut();
        });
    },
    //搜索
    searchBtnFun : function(){
        var that = this;
        var sBtn = that.searchBtn;
        sBtn.unbind("click");
        sBtn.bind("click",function(){
            var searchVal = that.search.find("input").val(),
                searchInitVal = that.search.find("input").attr("initval");
            var sCon = searchVal;
            that.searchFun(sCon);
        });
    },
    //点击联想词搜索
    associateWordFun : function(){ 
        var that = this;
        var aTri = that.associateWord.find("a");
        aTri.unbind("mousedown");
        aTri.bind("mousedown",function(){
            var _this = $(this);
            var sCon = _this.text();

            if(_this.find("i").length > 0){
                sCon = _this.find("i").text();
            }

            that.searchFun(sCon);
        })
    },
    searchFun : function(sCon){
        var that = this;
        var sCon = sCon || "";
        window.location.href = '/search/do_search?text=' + sCon;
    },
    //获取浏览器带有的参数
    getUrlDate : function(){
        var that = this;
        var urlData = {};
        var search = window.location.search != "" ? window.location.search.split("?")[1] : "";
        var searchStr = (search != "") && (search != undefined) ? search.split("&") : '';

        if(searchStr.length > 0){
            for(var i=0;i<searchStr.length;i++){
                var splitStr = searchStr[i].split("=");
                var keyStr = splitStr[0];
                urlData[keyStr] = splitStr[1];
            }
        }

        return urlData;
    }
}
