jQuery(function($) {
 //   'use strict',

    // #main-slider
    $('.carousel').carousel({
    // interval: 300
    });

    $(".animate-num").addClass("animate-active");
    var toggleFlag = 0;

    window.onscroll = function() {
       // $(".animate-num").toggleClass("animate-active");
        try{
            var winScroll = document.documentElement.scrollTop || document.body.scrollTop;
            if(winScroll<300){
                toggleFlag=0;
                $(".animate-num").removeClass("animate-active");
            }
            if ((winScroll >= 300) && (toggleFlag==0)) {
                $(".animate-num").addClass("animate-active");
                toggleFlag=1;

                var countUpNum1 = new CountUp("countUpNum1", 00,20);
                var countUpNum2 = new CountUp("countUpNum2", 000,500);
                var countUpNum3 = new CountUp("countUpNum3", 000,250);
                var countUpNum4 = new CountUp("countUpNum4", 000,100);
                countUpNum1.start();
                countUpNum2.start();
                countUpNum3.start();
                countUpNum4.start();
            }
        }catch (e){
            console.log("Error Message : [ window.onscroll]"+ e.message);
        }

    };

    try{
        // Initiat WOW JS
        if(!isIE8)
            new WOW().init();
    }catch (e){
        console.log("Error Message : [WOW().init()]"+ e.message);
    }

    try{

    }catch (e){
        console.log("Error Message : "+ e.message);
    }

    try{
        var cpcn ={
            width: $(window).width(),
            height: $(window).height(),
            bannerRate: 890 / 1920
        };

        cpcn.getScreenSize = function () {
            this.width = $(window).width();
            this.height = $(window).height();
        };

        cpcn.setFirstPage = function () {

            cpcn.getScreenSize();

            var businessH = 210, topH = 90;
            if(this.height<=700) {//height <= 700px
                businessH = 124;
                $("#businessH").css("padding-top","10px");
                $(".carousel-content").css("margin-top","155px");
                $("#main-slider.carousel h1").css("font-size","48px");
                $("#main-slider.carousel h2").css("font-size","18px");
                $(".placeholderLogin").css("height","30px");
                $(".placeholderSubTitle").css("height","30px");
                $(".specFont1").css("margin-left","-20px");
                $(".specFont2").css("margin-left","-13px");
                $("#businessH").css("height","124px");
            }else if(this.height>700 && this.height<=900) { //height > 700px <= 900px
                $("#businessH").css("padding-top","45px");
                $(".carousel-content").css("margin-top","155px");
                $("#main-slider.carousel h1").css("font-size","60px");
                $("#main-slider.carousel h2").css("font-size","20px");
                $(".placeholderLogin").css("height","80px");
                $(".placeholderSubTitle").css("height","30px");
                $(".specFont1").css("margin-left","-30px");
                $(".specFont2").css("margin-left","-20px");
                $("#businessH").css("height","210px");
            }else { //height > 900px
                $(".carousel-content").css("margin-top","225px");
                $("#main-slider.carousel h1").css("font-size","72px");
                $("#main-slider.carousel h2").css("font-size","24px");
                $(".placeholderLogin").css("height","180px");
                $(".placeholderSubTitle").css("height","65px");
                $(".specFont1").css("margin-left","-40px");
                $(".specFont2").css("margin-left","-20px");
                $("#businessH").css("height","210px");
            }
            /*
            if(this.height>769){
                $(".carousel-content").css("margin-top","225px !important");

                $("#businessH").css("height","210px");

            }
            */

            var bannerH = this.height - businessH + topH;
            $("#main-slider .item").css("height",bannerH+"px");
            $("#main-slider").css("height",bannerH+"px");
          //  $("#businessH").css("height",businessH+"px");

        };

        cpcn.init = function () {

            cpcn.setFirstPage();

        }();

        $(window).resize(function () {

            cpcn.getScreenSize();

            cpcn.setFirstPage();

            //  $('.wedo_list ul').isotope('reLayout');

        });

      //  if (isSupportedBro && (indexFlag == 'false' || typeof(indexFlag) == "undefined")) {
            $("body").queryLoader2({
                backgroundColor: "#fff",
                barColor: "#da251d",
                barHeight: 2,
                percentage: true,
                onComplete: function () {

                    $(".qLoverlay").fadeOut();

                }
            });
      //  }
    }catch (e){
        console.log("Error Message : [cpcn.setFirstPage]"+ e.message);
    }


});
