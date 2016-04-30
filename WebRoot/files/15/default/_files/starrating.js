var rateMessage = {
		'rate-1': {
				'rate-1': '很差',
				'rate-2': '勉强',
				'rate-3': '一般',
				'rate-4': '好',
				'rate-5': '很好'
		}
};
$().ready(function () {
		var starInit = $("#starInit");
		var ulStars = $("#ulStars");
		var txtStar = $("#txtStar");
		var tip = $("#tip");
		var rate_1_result = $("#rate_1_result");
		var star_wrap = $("#star_wrap");
		var oLis = $("#ulStars li");
		oLis.each(function (i) {
				$(this).click(function () {
						var iStar = parseInt($(this).attr("star"), 10);
						txtStar.val(iStar);
						rate_1_result.html("<span style='color:red'>" + iStar + " 分</span> - " + rateMessage["rate-1"]["rate-" + iStar]);
				}).hover(function () {
						var iStar = parseInt($(this).attr("star"), 10);
						for (var i = 0; i < oLis.length; i++) {
								var _temp = oLis[i];
								if (_temp.attributes["star"].value <= iStar) {
										if (iStar >= 3) {
												_temp.className = "good";
										}
										else {
												_temp.className = "bad";
										}
								}
								else {
										_temp.className = "";
								}
						}
				}, function () {
						if (txtStar.val() != "") {
								var iSelectedStar = parseInt(txtStar.val(), 10);
								for (var i = 0; i < oLis.length; i++) {
										var _temp = oLis[i];
										if (_temp.attributes["star"].value > iSelectedStar) {
												_temp.className = "";
										}
										else {
												var iSelfStar = parseInt(_temp.attributes["star"].value, 10);
												if (iSelfStar >= 3) {
														_temp.className = "good";
												}
												else {
														if (iSelectedStar >= 3) {
																_temp.className = "good";
														}
														else {
																_temp.className = "bad";
														}
												}
										}
								}
						}
				}).mousemove(function (e) {
						var intX = 0, intY = 0;
						if (e == null) {
								e = window.event;
						}
						if (e.pageX || e.pageY) {
								intX = e.pageX; intY = e.pageY;
						}
						else if (e.clientX || e.clientY) {
								if (document.documentElement.scrollTop) {
										intX = e.clientX + document.documentElement.scrollLeft;
										intY = e.clientY + document.documentElement.scrollTop;
								}
								else {
										intX = e.clientX + document.body.scrollLeft;
										intY = e.clientY + document.body.scrollTop;
								}
						}
						var tipbar = tip.get(0);
						tipbar.style.top = (intY + 20) + "px";
						tipbar.style.left = (intX - 95) + "px";
						tipbar.style.display = "";

						var iStar = parseInt($(this).attr("star"), 10);
						tip.html("<span style='color:red'>" + iStar + " 分</span> - " + rateMessage["rate-1"]["rate-" + iStar]);

				}).mouseout(function () {
						tip.hide();
				})
		})
		star_wrap.hover(function () { }, function () {
				setTimeout(initStar, 50);
		})
		ulStars.hover(function () { }, function () { setTimeout(initStar, 50); });
		var initStar = function () {
				if (txtStar.val() == "") {
						for (var i = 0; i < oLis.length; i++) {
								var _temp = oLis[i];
								_temp.className = "";
						}
				}
		}
})  
