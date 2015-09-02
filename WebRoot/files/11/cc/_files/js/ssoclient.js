$(function() 
{
    var client_id = $("#client_id").val();
    var site_url = $("#base_url").val();
    var sso_server_url = $("#sso_server_url").val();
    var sso_is_signin_url = sso_server_url+'sso/is_signin?client_id='+client_id;
    var sso_signin_url = $("#sso_signin_url").val();
    var sso_signup_url = $("#sso_signup_url").val();    
    var client_js_logout_url = site_url+'user/js_logout';
    var client_savesession_url = site_url+'user/st_validate?mode=js';
    //SSO登录状态
    $.getScript(sso_is_signin_url,
        function()
        {
            var oResult = '';
            var status = '';
            var st = '';
            if(result)
            {
                status = result.sign_status;
                st = result.st;
            }
            if(status == 'yes' && st !='')
            {
                //本地用户登录信息为空,保存用户登录信息
                client_savesession_url = client_savesession_url+'&st='+st;
                $.getScript(client_savesession_url,
                    function()
                    {
                    });          
            }
            else
            {
                //退出
                $.get(client_js_logout_url,{},function(msg){
                    if(msg == 'logout'){
                        window.location.reload();
                    }
                });
            }
        });
    
});
