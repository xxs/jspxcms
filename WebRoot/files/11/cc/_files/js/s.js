(function (sid, dataUrl, pwjsondata, murl, isrc, ajs, v) {
	function R(i, t) {
		return window[i] ? window[i] : window[i] = t
	}
	var doc = document,
	win = window,
	nav = navigator,
	locs = "" + location,
	doShow = function () {
		this.init()
	};
	doShow.prototype = {
		init : function () {
			var i = this.getOrientation(win.CNZZ_ADM_Orientation || ""),
			t = this.isFlash(),
			n = screen.width,
			e = screen.height,
			o = encodeURIComponent(doc.referrer),
			a = "",
			r = this.getDomian(),
			s = (new Date).getMilliseconds(),
			c = +new Date + "" + s,
			W = window,
			WD = W.CNZZ_SLOT_MAP||'',
			ib = (WD && WD[sid])||'';
			if(ib && typeof CNZZ_SLOT_RENDER === 'function'){
				CNZZ_SLOT_RENDER(sid);
				return false;
			}
			locs.match(/http:\/\/.+/) && (a = encodeURIComponent(locs)),
			dataUrl = dataUrl + "?sid=" + sid + "&width=" + n + "&height=" + e + i + "&isf=" + t + "&domain=" + r + pwjsondata + "&time=" + c + "&referer=" + o + "&href=" + a,
			this.doLoadScript();
		},
		isFlash : function () {
			var hasflash = 0;
			if (nav.plugins && nav.plugins.length) {
				for (var ii = 0; nav.plugins.length > ii; ii++)
					if (-1 != nav.plugins[ii].name.indexOf("Shockwave Flash")) {
						hasflash = 1;
						break
					}
			} else if (win.ActiveXObject && !win.opera)
				for (var ii = 12; ii >= 2; ii--)
					try {
						var fl = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + ii + "');");
						if (fl) {
							hasflash = 1;
							break
						}
					} catch (e) {}

			return hasflash
		},
		getOrientation : function (i) {
			if ("" != i) {
				for (var t, n = [], e = /([0-9a-zA-Z_]+\((?:[0-9a-zA-Z_,]+?)\))\|*/; e.test(i); ) {
					var o = RegExp.$1;
					o = o.substr(0, o.length - 1).replace(/[\(|\)]/g, ","),
					n.push(o),
					i = i.replace(e, "")
				}
				return i = n.join("|"),
				t = "&customOion=" + i
			}
			return ""
		},
		getDomian : function () {
			var i = "",
			t = doc.referrer;
			if ("" != t) {
				var n = /(\w+):\/\/([^\/:]+)(:\d*)?([^#]*)/,
				e = t.match(n);
				i = e[2]
			}
			return i
		},
		doLoadScript : function () {
			var queueid = win.queueid = win.queueid || 1,excludeid='';
			win["starttime_" + sid] = +new Date,
			win["limit_" + sid] = 0,
			dataUrl += "&queueid="+queueid;
			if(!win.CNZZ_JSFILE && isrc){
				var jsfile = isrc+"/js/"+ajs+"?v="+v;
				
				document.write('<script type="text/javascript" charset="gb2312" src="' + jsfile + '"></scr' + "ipt>");
			}
			if(win.CNZZ_SLOT_MAP){
				for(var i in CNZZ_SLOT_MAP){
					excludeid += i + ",";
				}
				dataUrl += "&excludeid="+excludeid;
			}
			document.write('<script type="text/javascript" charset="gb2312" src="' + dataUrl + '"></scr' + "ipt>");
			win.queueid++;
			cnzz_check_js(sid);
		}
	},
	R("cnzz_check_js", function (i) {
		var t = arguments.callee,
		n = murl + "?sid=" + i + "&type=js",
		e = 100,
		o = !0;
		return win["totaltime_" + i] ? n += "&loadtime=" + win["totaltime_" + i] : win["limit_" + i] && win["limit_" + i] > 4 ? n += "&loadtime=-1" : o = !1,
		o ? (setTimeout(function () {
				cnzz_request(n)
			}, 0), !1) : (setTimeout(function () {
				t(i),
				win["limit_" + i]++
			}, e), void 0)
	}),
	R("cnzz_request", function (i) {
		var n=10000,r = parseInt(Math.random()*n+1);
		if(r>=n/10){
			return false;
		}
		if (!i)
			return !1;
		var t = new Image,
		n =  + (new Date).getMilliseconds();
		i = i + "&rtime=" + n,
		t.onload = t.onabort = t.onerror = function () {
			t = null
		},
		t.src = i
	});
	var doShowObj = new doShow
})(382189,'http://js.adm.cnzz.net/aroute.php','&proid=&pid=&fid=&mid=&floorid=','http://action.adm.cnzz.net/bench.gif','http://js.adm.cnzz.net','s.js',20140108);