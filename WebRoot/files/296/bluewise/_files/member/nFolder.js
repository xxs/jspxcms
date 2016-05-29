// JavaScript Document
function getDomainList(type){
	if(type=='1'||type=='0'){
		$.get("/ajaxs/getDomainList.ajax.php",function(data){
			if(data!=null){
				$("#domainlist").html('<li class="list-items"><a url="nDomainList.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有域名</a></li>'+data);
				setLesten2();
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	} 
	if(type=='2'||type=='0'){
		$.get("/ajaxs/getVhostList.ajax.php?prefix="+window.FolderListTypeShow,function(data){
			if(data!=null){
				switch(window.FolderListTypeShow){
					case 'H':
						//主机
						$("#vhostlist").html('<li class="list-items"><a url="nVhost.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有主机</a></li>'+data);
						break;
					case 'F':
						//魔方
						$("#vhostlist").html('<li class="list-items"><a url="nVhostPifa.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有主机</a></li>'+data);
						break;
					case 'D':
						//数据库
						$("#vhostlist").html('<li class="list-items"><a url="nDB_list.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有数据库</a></li>'+data);
						break;
				}
				setLesten3();
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	}
	if(type=='3'||type=='0'){
		$.get("/ajaxs/getVhostList.ajax.php?prefix=E",function(data){
			if(data!=null){
				$("#youjulist").html('<li class="list-items"><a url="nEmail.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有邮局</a></li>'+data);
				setLesten4();
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	}
	if(type=='4'||type=='0'){
		$.get("/ajaxs/getVhostList.ajax.php?prefix=X",function(data){
			if(data!=null){
				$("#folderX").html('<li class="list-items"><a url="nNicebox.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有宝盒</a></li>'+data);
				setLestenFolder('X');
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	}
	if(type=='5'||type=='0'){
		$.get("/ajaxs/getVhostList.ajax.php?prefix="+window.FolderListTypeShowServer,function(data){
			if(data!=null){
				switch(window.FolderListTypeShowServer){
					case 'Y':
						//云
						$("#folderY").html('<li class="list-items"><a url="nCloud.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有主机</a></li>'+data);
						break;
					case 'I':
						//独立
						$("#folderY").html('<li class="list-items"><a url="nNidc.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有主机</a></li>'+data);
						break;
				}
				setLestenFolder('Y');
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	}
	/*if(type=='6'||type=='0'){ 
		$.get("/ajaxs/getVhostList.ajax.php?prefix=C",function(data){
			if(data!=null){
				
				$("#folderC").html('<li class="list-items"><a url="nNc_list.php?folder=all" class="list-items-link"><i class="fa fa-folder"></i>所有网站秘书</a></li>'+data);
				
				setLestenFolder('C');
				if(!window.isMobile){$("#left-menu-two").getNiceScroll().resize();}
			}
		});
	}*/
}
var FolderListTypeShowServer='Y';
var FolderListTypeShow='H';
function changeFolderListShow(type){
	switch(type){
		case 'H':
			//主机
			$("#folder-title-show").html("主机文件夹<a href=\"javascript:void(0)\" class=\"fa fa-plus-square\" style=\"color: #aaa;vertical-align: -1px;padding-left: 8px;text-indent: 0;\" onClick=\"return openWindows(0,'','H');\"></a>");
			window.FolderListTypeShow='H';
			break;
		case 'F':
			//魔方
			$("#folder-title-show").html("魔方文件夹<a href=\"javascript:void(0)\" class=\"fa fa-plus-square\" style=\"color: #aaa;vertical-align: -1px;padding-left: 8px;text-indent: 0;\" onClick=\"return openWindows(0,'','F');\"></a>");
			window.FolderListTypeShow='F';
			break;
		case 'D':
			//数据库
			$("#folder-title-show").html("数据库文件夹<a href=\"javascript:void(0)\" class=\"fa fa-plus-square\" style=\"color: #aaa;vertical-align: -1px;padding-left: 8px;text-indent: 0;\" onClick=\"return openWindows(0,'','D');\"></a>");
			window.FolderListTypeShow='D';
			break;
	}
	getDomainList('2');
}
function changeFolderListServerShow(type){
	switch(type){
		case 'Y':
			//云主机
			$("#folder-title-show-server").html("云主机文件夹<a href=\"javascript:void(0)\" class=\"fa fa-plus-square\" style=\"color: #aaa;vertical-align: -1px;padding-left: 8px;text-indent: 0;\" onClick=\"return openWindows(0,'','Y');\"></a>");
			window.FolderListTypeShowServer='Y';
			break;
		case 'I':
			//独立服务器
			$("#folder-title-show-server").html("独立主机文件夹<a href=\"javascript:void(0)\" class=\"fa fa-plus-square\" style=\"color: #aaa;vertical-align: -1px;padding-left: 8px;text-indent: 0;\" onClick=\"return openWindows(0,'','I');\"></a>");
			window.FolderListTypeShowServer='I';
			break;
	}
	getDomainList('5');
}
function delFolder(id,name){
	if(window.confirm('确定删除 '+name+' 文件夹吗？')){
		$.post('../ajaxs/folderAction.ajax.php',{'act':'delfolder','folderid':id},function(rdate){
			if(rdate==null){
				showMsg("网络连接失败！请重试！");
				return;	
			}else if(rdate.flag=='1'){
				getDomainList('0');
				if(rdate.msg!=''){
					//rdate.msg=decodeURI(rdate.msg);
					showMsg(rdate.msg);
				}else{
					showMsg('操作成功');
				}
				return;	
			}else if(rdate.msg!=''){
				//rdate.msg=decodeURI(rdate.msg);
				showMsg(rdate.msg);
				return;	
			}else{
				showMsg("网络连接失败！请重试！");
				return;	
			}
		},"json").error(function(){
			showMsg("网络连接失败！请重试！");
			return;	
		});
	}
}
var folderType='0';//folderid
var subFolderType=0;
function subFolder(){
	/*if(window.subFolderType==0){*/
		if(window.folderType=='0'){
			//--------新建
			if($("#folderName").val()==''){
				alert("请填写文件夹名称");
				return;	
			}
			$.post('../ajaxs/folderAction.ajax.php',{'act':'addfolder','prefix':window.subFolderType,'newfolder':$("#folderName").val()},function(rdate){
				if(rdate==null){
					showMsg("网络连接失败！请重试！");
					return;	
				}else if(rdate.flag=='1'){
					closeWindows();
					getDomainList('0');
					if(rdate.msg!=''){
						//rdate.msg=decodeURI(rdate.msg);
						showMsg(rdate.msg);
					}else{
						showMsg('操作成功');
					}
					return;	
				}else if(rdate.msg!=''){
					//rdate.msg=decodeURI(rdate.msg);
					showMsg(rdate.msg);
					return;	
				}else{
					showMsg("网络连接失败！请重试！");
					return;	
				}
			},"json").error(function(){
				showMsg("网络连接失败！请重试！");
				return;	
			});
			//--------------
		}else{
			//--------------编辑
			if($("#folderName").val()==''){
				alert("请填写文件夹名称");
				return;	
			}
			$.post('../ajaxs/folderAction.ajax.php',{'act':'modfolder','folderid':window.folderType,'foldername':$("#folderName").val()},function(rdate){
				if(rdate==null){
					showMsg("网络连接失败！请重试！");
					return;	
				}else if(rdate.flag=='1'){
					closeWindows();
					getDomainList('0');
					if(rdate.msg!=''){
						//rdate.msg=decodeURI(rdate.msg);
						showMsg(rdate.msg);
					}else{
						showMsg('操作成功');
					}
					return;	
				}else if(rdate.msg!=''){
					//rdate.msg=decodeURI(rdate.msg);
					showMsg(rdate.msg);
					return;	
				}else{
					showMsg("网络连接失败！请重试！");
					return;	
				}
			},"json").error(function(){
				showMsg("网络连接失败！请重试！");
				return;	
			});
		}
	/*}*/
	/*else{
		if(window.folderType=='0'){
			if($("#folderName").val()==''){
				alert("请填写文件夹名称");
				return;	
			}
			$.post('../ajaxs/folderAction.ajax.php',{'act':'addfolder','prefix':'H','newfolder':$("#folderName").val()},function(rdate){
				if(rdate==null){
					showMsg("网络连接失败！请重试！");
					return;	
				}else if(rdate.flag=='1'){
					closeWindows();
					getDomainList(1);
					getDomainList(2);
					if(rdate.msg!=''){
						//rdate.msg=decodeURI(rdate.msg);
						showMsg(rdate.msg);
					}else{
						showMsg('操作成功');
					}
					return;	
				}else if(rdate.msg!=''){
					//rdate.msg=decodeURI(rdate.msg);
					showMsg(rdate.msg);
					return;	
				}else{
					showMsg("网络连接失败！请重试！");
					return;	
				}
			},"json").error(function(){
				showMsg("网络连接失败！请重试！");
				return;	
			});
		}else{
			if($("#folderName").val()==''){
				alert("请填写文件夹名称");
				return;	
			}
			$.post('../ajaxs/folderAction.ajax.php',{'act':'modfolder','folderid':window.folderType,'foldername':$("#folderName").val()},function(rdate){
				if(rdate==null){
					showMsg("网络连接失败！请重试！");
					return;	
				}else if(rdate.flag=='1'){
					closeWindows();
					getDomainList(1);
					getDomainList(2);
					if(rdate.msg!=''){
						//rdate.msg=decodeURI(rdate.msg);
						showMsg(rdate.msg);
					}else{
						showMsg('操作成功');
					}
					return;	
				}else if(rdate.msg!=''){
					//rdate.msg=decodeURI(rdate.msg);
					showMsg(rdate.msg);
					return;	
				}else{
					showMsg("网络连接失败！请重试！");
					return;	
				}
			},"json").error(function(){
				showMsg("网络连接失败！请重试！");
				return;	
			});
		}
	}
	*/
}
function showMsg(msg){
	$(".windowMsg span").html(msg);
	$(".windowMsg").css({"display":"block","opacity":0}).stop().animate({opacity:1},500,function(){
		setTimeout(function(){
			$(".windowMsg").stop().animate({opacity:0},500,function(){
				$(".windowMsg").css({"display":"none"});
			});
		},3000);
	});
}
$(function(){
	/*getDomainList('0');
	ajaxshowmsg();
	if(!isMobile){
		ajaxshowshop();
	}*/
});
$(window).load(function(e) {
    getDomainList('0');
	ajaxshowmsg();
	if(!isMobile){
		ajaxshowshop();
	}
});
function ajaxshowshop(){
	setTimeout(function(){
	$.get("/cart/shoppingcart_ajax.php",{'act':'getcountnumonly'},function(data){
		if(data!=null){
			if(data.flag=="1"){
				$('#showcount').html(data.num);
				setTimeout(ajaxshowshop,29500);
			}
		}
	},"json");
	},500);
}
function ajaxshowmsg(){
	setTimeout(function(){
	$.get("/ajaxs/getNum.ajax.php",{'act':'getNum'},function(data){
		if(data!=null){
			if(data.flag=="1"){
				$('#showMsgCount').html(data.msgCount);
				$('#wt-num').html(data.gdCount);
				$("#ye-num").html(data.userMoney);
				setTimeout(ajaxshowmsg,29500);
			}
		}
	},"json");
	},500);
}
function closeWindows(){
	$("#showWindows_1").stop().animate({opacity:0},500,function(){
		$("#showWindows_1").css({"display":"none"});
	});
	return false;
}
function closeWindows2(){
	$("#showWindows_2").stop().animate({opacity:0},500,function(){
		$("#showWindows_2").css({"display":"none"});
	});
	return false;
}
function openWindows(type,name,folder){
	window.subFolderType=folder;
	window.folderType=type;
	if(type=='0'){
		$("#showWindows_1 #formBox-title").html('添加文件夹');
		$("#showWindows_1 #formBox-name").html('文件夹名：');
		$("#folderName").val('');
	}else{
		$("#showWindows_1 #formBox-title").html(name+' 文件夹重命名');
		$("#showWindows_1 #formBox-name").html('文件夹名：');
		$("#folderName").val(name);
	}
	$("#showWindows_1").css({"display":"block","opacity":0}).stop().animate({opacity:1},500);
	return false;
}
function openWindows2(ids,prefix){
	$("#suffix_father").html('<select class="sec suffixCheack" name="suffix[]" onChange="folderListen(this)"><option value="">正在加载文件夹列表...</option></select>');
	$("#formBox-name2").html('&nbsp;');
	$("#folderName2").css({"display":"none"});
	$("#prefix_N").val(prefix);
	$("#ids_N").val(ids);
	$("#folderName2").val('');
	getDomainFolderList(prefix);
	$("#showWindows_2").css({"display":"block","opacity":0}).stop().animate({opacity:1},500);
	return false;
}
function getDomainFolderList(prefix){
	$.get('../ajaxs/getDomainFolder.ajax.php',{'prefix':prefix},function(redate){
		if(redate!=""){
			$("#suffix_father").html(redate);
			if(redate.length==('<select onchange="folderListen(this)" name="suffix[]" class="sec suffixCheack"><option value="+">+添加到新文件夹</option></select>').length){
				$("#formBox-name2").html('新文件夹名：');
				$("#folderName2").css({"display":"block"});
			}
		}else{
			closeWindows2();
			showMsg("网络连接失败！请重试！");
		}
	}).error(function(){
		closeWindows2();
		showMsg("网络连接失败！请重试！");
	});
}
function folderListen(obj){
	if($(obj).val()=="+"){
		$("#formBox-name2").html('新文件夹名：');
		$("#folderName2").css({"display":"block"});
	}else{
		$("#formBox-name2").html('&nbsp;');
		$("#folderName2").css({"display":"none"});
	}
}
function subFolder2(){
	var folder=$(".suffixCheack").val();
	var prefix_N=$("#prefix_N").val();
	var ids_N=$("#ids_N").val();
	var folderName2=$("#folderName2").val();
	if(folder==''){
		showMsg("请选择添加到的文件夹！");
		return false;
	}
	if(folder=='+'){
		if(folderName2==''){
			showMsg("请填写文件夹名称！");
			return false;	
		}
	}
	$.post('../ajaxs/folderAction.ajax.php',{'act':'add','folderid':folder,'prefix':prefix_N,'ids':ids_N,'newfolder':folderName2},function(rdate){
		if(rdate==null){
			showMsg("网络连接失败！请重试！");
			return;	
		}else if(rdate.flag=='1'){
			closeWindows2();
			getDomainList('0');
			if(rdate.msg!=''){
				//rdate.msg=decodeURI(rdate.msg);
				showMsg(rdate.msg);
			}else{
				showMsg('操作成功');
			}
			return;	
		}else if(rdate.msg!=''){
			//rdate.msg=decodeURI(rdate.msg);
			showMsg(rdate.msg);
			return;	
		}else{
			showMsg("网络连接失败！请重试！");
			return;	
		}
	},"json").error(function(){
		showMsg("网络连接失败！请重试！");
		return;	
	});
}
function delFormFolder(ids,folder){
	if(window.confirm('确定要移出文件夹吗？')){
		loadTipsStart();
		$.get('../ajaxs/folderAction.ajax.php',{'act':'del','folderid':folder,'ids':ids},function(rdate){
			if(rdate==null){
				loadTipsEnd();
				showMsg("网络连接失败！请重试！");
				return;	
			}else if(rdate.flag=='1'){
				loadTipsEnd();
				if(rdate.msg!=''){
					showMsg(rdate.msg);
				}else{
					showMsg('操作成功');
				}
				getDomainList('0');
				document.getElementById('frame').contentWindow.location.reload(true);
				return;	
			}else if(rdate.msg!=''){
				loadTipsEnd();
				showMsg(rdate.msg);
				return;	
			}else{
				loadTipsEnd();
				showMsg("网络连接失败！请重试！");
				return;	
			}
		},"json").error(function(){
			loadTipsEnd();
			showMsg("网络连接失败！请重试！");
		});
	}
}