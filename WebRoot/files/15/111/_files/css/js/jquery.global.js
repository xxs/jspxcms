
$(function(){
$('#topMenuLogin,.windows,.message,.buy_car').hover(function(){
			$(this).css('color','#333').css('background','#fff');
			$(this).children('a').css('color','#333');
			$(this).find('.enarr_t').hide();
			$(this).find('#logimg').attr("src","/images/v6/2015/loginico_h.png");
			$(this).find('.buycarImg').attr("src","/images/v6/2015/buycar_h.png");
			

	    $(this).find('.num').hide();
		$(this).find('.be_login_name').css('color','#333')
		},
		function(){
			$(this).css('color','#ddd').css('background','');
			$(this).children('a').css('color','#ddd');
			$(this).parent('#topMenuLogin').css('color','#fff').css('background','');
			$('.windows').css('background','#fff');
			$(this).find('.enarr_t').show();
			$(this).parent('#topMenuLogin,.message').find('.enarr_t').show();
			$(this).find('#logimg').attr("src","/images/v6/2015/loginico.png");
			$(this).parent('#topMenuLogin,.message').find('#logimg').attr("src","/images/v6/2015/loginico.png");
			$(this).find('.buycarImg').attr("src","/images/v6/2015/buycar.png");
		$(this).find('.be_login_name').css('color','#fff');
		$(this).find('.num').show();
		/*$(this).find('.num').show();
		$('.buy_car').css('margin-right','23px');
	$('.message ').css('margin-right','13px');*/
	});					
		
	$('.lang').hover(function(){
		$('.default_web').css('color','#333');
		$(this).css('color','#333').css('background','#fff').css('padding','0 20px');
		$(this).find('.enarr_t').hide();
	},function(){
		$('.default_web').css('color','#ddd');
		$(this).css('color','#fff').css('background','').css('padding','0');
		$(this).find('.enarr_t').show();
	});
 
	$("#aFloatTools_Show").click(function(){
			$('#floatTools').animate({right:'0px'},400);
			$('#aFloatTools_Show').hide();
			$('#aFloatTools_Hide').show();				
		});
		$("#aFloatTools_Hide").click(function(){
			$('#floatTools').animate({right:'-150px'},400);
			$('#aFloatTools_Show').show();
			$('#aFloatTools_Hide').hide();	
		});
 });




// JavaScript Document GOBack���ض���

function goTopEx(show_right_now){
        var obj=document.getElementById("goTopBtn");
        function getScrollTop(){
                return $(document).scrollTop();
            }
        function setScrollTop(value){
                $(document).scrollTop(value);
            }    
		if(!show_right_now){
			$(window).scroll(function(){
				getScrollTop()>0?obj.style.display="":obj.style.display="none";
			});
		}else obj.style.display="";
        //window.onscroll=function(){getScrollTop()>0?obj.style.display="":obj.style.display="none";}
        obj.onclick=function(){
            var goTop=setInterval(scrollMove,1);
            function scrollMove(){
                    setScrollTop(getScrollTop()/1.1);
                    if(getScrollTop()<1) clearInterval(goTop);
                }
        }
		obj.onmouseover=function(){
			$("#top_home_img").attr("src", "/vhost/images/vhostDetail/top2_ico.gif");
		}
		obj.onmouseout=function(){
			$("#top_home_img").attr("src", "/vhost/images/vhostDetail/top_ico.gif");
		}
    }
