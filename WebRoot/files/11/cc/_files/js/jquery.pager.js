/*
* jQuery pager plugin
* Version 1.0 (12/22/2008)
* @requires jQuery v1.2.6 or later
*
* Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
*
* Copyright (c) 2008-2009 Jon Paul Davies
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
* 
* Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
*
* This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
*
* Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickCallback: PagerClickTest });
*
* Where pagenumber is the visible page number
*       pagecount is the total number of pages to display
*       buttonClickCallback is the method to fire when a pager button is clicked.
*
* buttonClickCallback signiture is PagerClickTest = function(pageclickednumber) 
* Where pageclickednumber is the number of the page clicked in the control.
*
* The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
* Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
*/
(function ($) {

    $.fn.pager = function (options) {

        var opts = $.extend({}, $.fn.pager.defaults, options);

        return this.each(function () {

            // empty out the destination element and then render out the pager with the supplied options
            $(this).empty().append(renderpager(parseInt(options.pagenumber), parseInt(options.pagecount), options.buttonClickCallback,opts.scrollPos));

            // specify correct cursor activity
            $('.tn-pagination a').mouseover(function () { 
					this.style.cursor = "pointer"; 
					$(this).addClass("hover"); })
				.mouseout(function () { 
						this.style.cursor = "auto";
						$(this).removeClass("hover");});
        });
    };

    // render and return the pager with the supplied options
    function renderpager(pagenumber, pagecount, buttonClickCallback, scrollPos) {

        // setup $pager to hold render
        var $pager = $('<div class="tn-pagination"></div>');

        // add in the previous and next buttons
        $pager.append(renderButton('home-page-number', pagenumber, pagecount, buttonClickCallback,scrollPos)).append(renderButton('last-page-number', pagenumber, pagecount, buttonClickCallback,scrollPos));

        // pager currently only handles 10 viewable pages ( could be easily parameterized, maybe in next version ) so handle edge cases
        var startPoint = 1;
        var endPoint = 5;

        if (pagenumber > 3) {
            startPoint = pagenumber - 2;
            endPoint = pagenumber + 2;
        }

        if (endPoint > pagecount) {
            startPoint = pagecount - 4;
            endPoint = pagecount;
        }

        if (startPoint < 1) {
            startPoint = 1;
        }

        // loop thru visible pages and render buttons
        for (var page = startPoint; page <= endPoint; page++) {

            var currentButton = $('<a class="tn-page-number"><em>' + (page) + '</em></a>');

            currentButton.appendTo($pager);
            // $("<em></em>").appendTo(currentButton);
            //currentButton.wrapInner("<em></em>");
            page == (pagenumber) ? currentButton.addClass('tn-selected') : currentButton.click(function (e){
				e.preventDefault(); 
                $("body,html").scrollTop(scrollPos);
				buttonClickCallback( $(this).children("em:first-child").html() );
				});
            
        }

        // render in the next and last buttons before returning the whole rendered control back.
        $pager.append(renderButton('next-page-number', pagenumber, pagecount, buttonClickCallback, scrollPos)).append(renderButton('trailer-page-number', pagenumber, pagecount, buttonClickCallback, scrollPos));

        return $pager;
    }

    // renders and returns a 'specialized' button, ie 'next', 'previous' etc. rather than a page number button
    function renderButton(buttonLabel, pagenumber, pagecount, buttonClickCallback, scrollPos) {
        var $Button = $('<a class="tn-page-next '+buttonLabel+'"><em></em></a>');

        var destPage = 1;

        // work out destination page for required button type
        switch (buttonLabel) {
            case "home-page-number":
                destPage = 1;
                break;
            case "last-page-number":
                destPage = pagenumber - 1;
                break;
            case "next-page-number":
                destPage = pagenumber + 1;
                break;
            case "trailer-page-number":
                destPage = pagecount;
                break;
        }

        // disable and 'grey' out buttons if not needed.
        if (buttonLabel == "home-page-number" || buttonLabel == "last-page-number") {
            pagenumber <= 1 ? $Button.addClass('pgEmpty') : $Button.click(function () { buttonClickCallback(destPage);$("body,html").scrollTop(scrollPos); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('pgEmpty') : $Button.click(function () { buttonClickCallback(destPage);$("body,html").scrollTop(scrollPos); });
        }

        return $Button;
    }

    // pager defaults. hardly worth bothering with in this case but used as placeholder for expansion in the next version
    $.fn.pager.defaults = {
        pagenumber: 1,
        pagecount: 1,
        scrollPos : 0 //默认翻页回到顶部
    };

})(jQuery);





