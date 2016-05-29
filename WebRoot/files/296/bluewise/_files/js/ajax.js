var req;
var divname="showdiv";
var _afterjs = '';
var _afterjs_clear = true;//2012-2-14
var _addhtmlafter = false;
function Initialize(){
	try{
		req=new ActiveXObject("Msxml2.XMLHTTP");
	}catch(e){
		try{
			req=new ActiveXObject("Microsoft.XMLHTTP");
		}catch(oc){
			req=null;
		}
	}

	if(!req&&typeof XMLHttpRequest!="undefined"){
		req=new XMLHttpRequest();
	}
}

function SetAfterExcute(js){
	_afterjs = js;
}

function SendQuery(url, name){
	Initialize();
	//alert(name);
	if(name) divname=name;
	if(document.getElementById(divname) && !document.getElementById(divname).innerHTML)document.getElementById(divname).innerHTML="<img src='/images/clocks.gif' align='absmiddle'> Loadding...";
	//ShowDiv(divname);
	if(req!=null){
		req.onreadystatechange = Process;
		req.open("GET", url, true);
        req.send(null);
	}
}

function SendQueryAddAfter(url, name){
	_addhtmlafter = true;
	SendQuery(url, name);
}

function PostQuery(url,name){
	Initialize();
	//alert(name);
	if(name) divname=name;
	//ShowDiv(divname);
	if(req!=null){
		req.onreadystatechange = Process;
		req.open("post", url, true);
        req.send(null);
	}
}

//2008-11-14
function ExecuteQuery(url){
	Initialize();
	if(req!=null){
		req.onreadystatechange = ProcessExecute;
		req.open("GET", url, true);
        req.send(null);
	}
}

function Process(){
	if(req.readyState == 4){
    // only if "OK"
		if (req.status == 200){
			if(req.responseText=="")
				HideDiv(divname);
			else{
				ShowDiv(divname);
		
				if(window.navigator.userAgent.indexOf("Firefox")>=1) //针对FF 
				{ 
					_SetAjaxHTML(req.responseText);
					var scripts = document.getElementById(divname).getElementsByTagName("script"); 
					for(var i=0;i <scripts.length;i++) //一段一段执行script 
					{ 
						eval(scripts[i].innerHTML); 
					}
				}else if(window.navigator.userAgent.indexOf("MSIE")!=-1) 
				{ 
					var httpText = req.responseText;
					httpText = httpText.replace(/<script[^>]*>/gi,"<script defer>")//因为 <script defer>可以在IE上直接运行，所以把所有的script标签都替换成defer标记的script 
					_SetAjaxHTML(httpText);
				}else{
					_SetAjaxHTML(req.responseText);
				}
			}
			if(_afterjs) eval(_afterjs);
		}else {
			_SetAjaxHTML("There was a problem retrieving data:<br>"+req.statusText);
		}
		if(_afterjs_clear) _afterjs = '';
	}
}

function _SetAjaxHTML(html){
	//alert(html);
	if(_addhtmlafter) document.getElementById(divname).insertAdjacentHTML("beforeEnd", html);
	else document.getElementById(divname).innerHTML = html;
	_addhtmlafter = false;
}
//2008-11-14
function ProcessExecute(){
	if(req.readyState == 4){
		if (req.status == 200){
			if(req.responseText==""){
			}else{
				eval(req.responseText);
			}
			if(_afterjs) eval(_afterjs);
		}else {
			alert("There was a problem retrieving data:<br>"+req.statusText);
		}
		_afterjs = '';
	}
}

function ShowDiv(divid) 
{
   if (document.layers) document.layers[divid].visibility="show";
   else document.getElementById(divid).style.visibility="visible";
}

function HideDiv(divid) 
{
   if (document.layers) document.layers[divid].visibility="hide";
   else document.getElementById(divid).style.visibility="hidden";
}

function OpenDiv(divid) 
{
	if (document.layers) document.layers[divid].style.display="";
	 else document.getElementById(divid).style.display="";
}

function ClostDiv(divid) 
{
	if (document.layers) document.layers[divid].style.display="none";
	 else document.getElementById(divid).style.display="none";
}