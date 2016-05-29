
function setCookie (name, value) {
  var the_date = new Date("December 31, 2023");
  var the_cookie_date =the_date.toGMTString();
  document.cookie = "expires=" + Date;
	
  document.cookie = name + "=" + escape(value);
}

function getCookie(name) {
var search;
search = name + "=";
offset = document.cookie.indexOf(search);
if (offset != -1) {
 offset += search.length;
 end = document.cookie.indexOf(";", offset);
 if (end == -1)
 end = document.cookie.length;
 return unescape(document.cookie.substring(offset, end));
 //alert(unescape(document.cookie.substring(offset, end)));
 }
else{
// alert("没有此Cookie内容");
 }
}

function deleteCookie(name) {
var expdate = new Date();
expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
setCookie(name, "", expdate);
}
