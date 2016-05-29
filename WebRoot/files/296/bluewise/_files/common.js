// JavaScript Document
function goTopEx(){
		 var obj=document.getElementById("goTopBtn");
		 width=(window.screen.width-1020)/2;
		 obj.style.right=width+'px';
		 obj.style.display="none";
        function getScrollTop(){
              return document.documentElement.scrollTop;
         }
        function setScrollTop(value){
              document.documentElement.scrollTop=value;
         }    
        window.onscroll=function(){getScrollTop()>0?obj.style.display="":obj.style.display="none";}
        obj.onclick=function(){
            var goTop=setInterval(scrollMove,10);
            function scrollMove(){
                    setScrollTop(getScrollTop()/1.5);
                    if(getScrollTop()<1)clearInterval(goTop);
                }
        }
	}
	$(function(){
		var objAs =$("a");
        var objA;
        for (var i = 0; objA = objAs[i]; i++) {
            objA.onfocus = function () { this.blur() };
        }
	});
	
	$(function(){
		var $span_link=$("span.linklabel");
		$span_link.each(function(i) {
      $(this).mouseover(function(){
				var index=$span_link.index(this);
				if($span_link.size()==4){
					$("ul.f5 li").eq(index).show().siblings().hide();
				}else{
					$("ul.f5").eq(index).show().siblings().hide();
				}
			});	
    });
	});