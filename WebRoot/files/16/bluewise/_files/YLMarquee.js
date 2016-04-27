 (function($) {
    $.fn.extend({
        "totalWidth": function() {
            var tmpWidth = 0;
            $(this).each(function() {
                tmpWidth += $(this).outerWidth(true);
            });
            return tmpWidth;
        },
        "totalHeight": function() {
            var tmpHeight = 0;
            $(this).each(function() {
                tmpHeight += $(this).outerHeight(true);
            });
            return tmpHeight;
        }
    });
    $.fn.YlMarquee = function(o) {
        o = $.extend({
            speed: 60,
            step: 3,
            vertical: false,
            width: 0,
            height: 0,
            visiable: 0,
            textMode: false
        }, o || {});
        var wrap = $(this), ul = $("ul", wrap), li = $("li", ul), v = o.visiable, step = o.step, liNum = li.size(), visiableLi = li.slice(0, v);
        var whiteSpace, floatStyle, displayStyle, liSize, ie7HackCss, marginStyle, paddingStyle, wrapSize, visiableLiSize, i, scrollSize, cssPro;
        if (o.vertical) {
            whiteSpace = "normal";
            floatStyle = "none";
            displayStyle = "block";
            wrapSize = o.height;
        } else {
            whiteSpace = "nowrap";
            floatStyle = "left";
            displayStyle = "inline";
            wrapSize = o.width;
            ie7HackCss = o.textMode ? "*float:none;" : "";
        }
        wrap.css({ position: "relative", overflow: "hidden" });
        ul.css({ position: "relative", "white-space": whiteSpace, overflow: "hidden", "list-style-type": "none", margin: "0", padding: "0" });
        li.css({ "white-space": whiteSpace, "display": displayStyle, overflow: "hidden" });
        li.attr("style", li.attr("style") + ";" + "float:" + floatStyle + ";" + ie7HackCss);
        liSize = o.vertical ? li.totalHeight() : li.totalWidth();
        o.vertical ? ul.height(liSize) : ul.width(liSize);
        visiableLiSize = o.vertical ? visiableLi.totalHeight() : visiableLi.totalWidth();
        if (wrapSize == 0) {
            wrapSize = visiableLiSize;
        }
        o.vertical ? wrap.height(wrapSize) : wrap.width(wrapSize);
        if (wrapSize < liSize) {
            ul.append(li.clone());
            var newLi = $("li", ul), newLiSize = o.vertical ? newLi.totalHeight() : newLi.totalWidth();
            newLi.attr("style", newLi.attr("style") + ";" + "float:" + floatStyle + ";");
            o.vertical ? ul.height(newLiSize) : ul.width(newLiSize);
            scrollSize = o.vertical ? newLi.slice(0, liNum).totalHeight() : newLi.slice(0, liNum).totalWidth();
            var MyMar = setInterval(marquee, o.speed);
            ul.hover(function() { clearInterval(MyMar); }, function() { MyMar = setInterval(marquee, o.speed); });
        }
        function marquee() {
            if (o.vertical) {
                if (wrap.scrollTop() >= scrollSize) {
                    wrap.scrollTop(wrap.scrollTop() - scrollSize + step);
                }
                else {
                    i = wrap.scrollTop();
                    i += step;
                    wrap.scrollTop(i)
                }
            } else {
                if (wrap.scrollLeft() >= scrollSize) {
                    wrap.scrollLeft(wrap.scrollLeft() - scrollSize + step);
                }
                else {
                    i = wrap.scrollLeft();
                    i += step;
                    wrap.scrollLeft(i);
                }
            }
        };
    };
})(jQuery);

