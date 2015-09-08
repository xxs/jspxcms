(function($) {
    $.fn.extend({
        capsLockTip: function(divTipID) {
            return this.each(function() {
                var ins = new $.CapsLockTip($(this));
                $(this).data(this.id, ins)
            })
        }
    });
    $.CapsLockTip = function(___target) {
        this.target = ___target;
        var _this = this;
        $(document).ready(function() {
            if (null == $.fn.capsLockTip.divTip) {
                $("body").append("<div id='divTip__985124855558842555' style='width:100px; height:15px; padding-top:3px; display:none; position:absolute; z-index:9999999999999; text-align:center; background-color:#FDF6AA; color:Red; font-size:12px; border:solid 1px #DBC492; border-bottom-color:#B49366; border-right-color:#B49366;'>大写锁定已打开</div>");
                $.fn.capsLockTip.divTip = $("#divTip__985124855558842555")
            }
            _this.target.bind("keypress", function(_event) {
                var e = _event || window.event;
                var kc = e.keyCode || e.which;
                var isShift = e.shiftKey || (kc == 16) || false;
                $.fn.capsLockTip.capsLockActived = false;
                if ((kc >= 65 && kc <= 90 && !isShift) || (kc >= 97 && kc <= 122 && isShift)) $.fn.capsLockTip.capsLockActived = true;
                _this.showTips($.fn.capsLockTip.capsLockActived)
            });
            _this.target.bind("keydown", function(_event) {
                var e = _event || window.event;
                var kc = e.keyCode || e.which;
                if (kc == 20 && null != $.fn.capsLockTip.capsLockActived) {
                    $.fn.capsLockTip.capsLockActived = !$.fn.capsLockTip.capsLockActived;
                    _this.showTips($.fn.capsLockTip.capsLockActived)
                }
            });
            _this.target.bind("focus", function(_event) {
                if (null != $.fn.capsLockTip.capsLockActived) _this.showTips($.fn.capsLockTip.capsLockActived)
            });
            _this.target.bind("blur", function(_event) {
                _this.showTips(false)
            })
        });
        this.showTips = function(display) {
            if (display) {
                var offset = _this.target.offset();
                $.fn.capsLockTip.divTip.css("left", offset.left + "px");
                $.fn.capsLockTip.divTip.css("top", offset.top + _this.target[0].offsetHeight + 3 + "px");
                $.fn.capsLockTip.divTip.show()
            } else {
                $.fn.capsLockTip.divTip.hide()
            }
        };
        $.fn.capsLockTip.divTip = null;
        $.fn.capsLockTip.capsLockActived = null
    }
})(jQuery);

//禁止输入空格
function forbidSpace() {
        //禁止登录注册用户名和密码输入空格：
        $("#loginform .ipt, #registerform .ipt, input[type='password']").on("keydown", function(event) {
            if (event.keyCode == 32) return false;
        })
    }
    //校验登录页面   注：该函数只是前端验证用户名和密码，不进行后端验证
function checkUserPassword() {
        var enterAccount = $("#enter_your_email").val();
        var enterPassword = $("#enter_your_password").val();
        // 登录验证
        var validator1 = $("#loginform").validate({
            rules: {
                account: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                account: {
                    required: enterAccount
                },
                password: {
                    required: enterPassword
                }
            },
            // the errorPlacement has to take the table layout into account
            errorPlacement: function(error, element) {
                if (element.is(":radio"))
                    error.appendTo(element.parent().next());
                else if (element.is(":checkbox"))
                    error.appendTo(element);
                else
                    error.appendTo(element.parent().next());
            },
            // set this class to error-labels to indicate valid fields
            success: function(label) {
                // set &nbsp; as text for IE
                label.html("&nbsp;").remove();
            },
            highlight: function(element, errorClass) {
                $(element).parent().next().find("." + errorClass).removeClass("checked");

            },
            form: function() {

            },
            focusCleanup: true,
            onkeyup: false,
            onfocusout: function(element, event) {
                validator1.element(element);
            },
            showErrors: function(errorMap, errorList) {
                this.defaultShowErrors();
            },
            debug: true
        });
        //return validator1.form();
    }
    //验证验证码函数
function checkVcode() {
        var enterCode = $("#enter_the_code").val();
        var vcodeInvalid = $("#vcode_invalid").val();
        vcode = $('#vcode').val();
        check_vcode_url = '/jsapi/auth/check_vcode';
        $.ajax({
            type: "post",
            dataType: "json",
            url: check_vcode_url,
            data: "vcode=" + vcode,
            success: function(data) {
                if ($('#vcode').attr('data-key') == 'true') {
                    if (!data.status) {
                        var err_msg = '';
                        if (vcode != '') {
                            err_msg = vcodeInvalid;
                        } else {
                            err_msg = enterCode;
                        }
                        if ($("#subcheck").length > 0) {
                            $("#subcheck").val('0');
                        }
                        $('#vcode').parents('tr').find('.error').show();
                        $('#vcode').parents('tr').find('.error').remove();
                        if ($('#vcode').parents('tr').find('.error').length > 0) {
                            $('#vcode').parents('tr').find('.error').html(err_msg);
                        } else {
                            $('#vcode').parents('tr').find('.tip').after('<label for="vcode" class="error">' + err_msg + '</label>');
                        }
                        return false;
                    } else {
                        if ($("#subcheck").length > 0) {
                            $("#subcheck").val('1');
                        }
                        $('#vcode').parents('tr').find('.error').addClass('checked');
                        $('#vcode').parents('tr').find('.error').html('');

                    }
                }
            }
        });
    }
    //判断是否输入验证码
function checkLForm() {
    //如果验证码未显示,检查是否需要输入验证码
    $('#password').blur();
    $('#account').blur();
    if ($('#vcodewrap').html() == '') //没有验证码框时，判断账号密码是否正确
    {
        $("#subcheck").val('0');
        checkAccount();
    } else {
        checkVcode();
    }
    if ($("#subcheck").val() == 1) return true;
    return false;

}

function checkAccount() {
        var account = $("#account").val();
        if (account) {
            $.ajax({
                type: "get",
                url: '/helper/check_account',
                data: {
                    "account": account
                },
                success: displayAccountInfo,
                dataType: "json",
                async: false
            });
        }
    }
    // 显示帐号状态
function displayAccountInfo(result) {
    if (result.status) {
        //帐号状态正常,可以登录
        $("#subcheck").val('1');
    } else {
        switch (result.info) {
            case 'show_vcode':
                //显示验证码
                if (!result.status) {
                    if ($('#vcodewrap').html() == '') {
                        $("#vcodewrap").html(result.data);
                    }
                } else {
                    $("#subcheck").val('1');
                    $("#vcodewrap").html("");
                }
                break;
                //
            case 'email':
                //
                $('#email').parents('tr').find('.error').show();
                $('#email').parents('tr').find('.error').remove();
                if ($('#email').parents('tr').find('.error').length > 0) {
                    $('#email').parents('tr').find('.error').html(err_msg);
                } else {
                    $('#email').parents('tr').find('.tip').after('<label for="email" class="error">' + result.err + '</label>');
                }
                break;
                //
        }
    }
}

function callback(responseText, statusText) {
        if (statusText == 'success') {
            $('.error').remove();
            $.each(responseText, function(i, obj) {
                if (i != 'signin') {
                    //表单验证
                    if (!obj.status) {
                        if ($('#' + i).parents('tr').find('.error').length > 0) {
                            $('#' + i).parents('tr').find('.error').html(obj.err);
                        } else {
                            $('#' + i).parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                        }
                    }
                } else {
                    //提交成功
                    if (obj.status) {
                        window.location.href = obj.data;
                    } else {
                        if ($('#loginsub').parents('tr').find('.error').length > 0) {
                            $('#loginsub').parents('tr').find('.error').html(obj.err);
                        } else {
                            $('#account').parents('tr').find('.tip').after('<label for="account" class="error">' + obj.err + '</label>');
                        }
                        $('#password').val('');
                    }
                }
            });
        }
        $("#loginsub").val($('#log_in').val());
    }
    //注册前端验证
function checkRegisterFront() {
        var emailEmpty = $("#email_empty").val();
        var notValidEmail = $("#not_valid_email").val();
        var usernameEmpty = $("#username_empty").val();
        var passwordEmpty = $("#password_empty").val();
        var passwordLength = $("#password_length").val();
        var passwordFormat = $("#password_format").val();
        var againPassword = $("#again_password").val();
        var twoPassword = $("#two_password").val();
        var notValidUsername = $("#not_valid_username").val();
        var emailLen = $("#email_len").val();
        var validator = $("#registerform").validate({
            rules: {
                email: {
                    required: true,
                    email: true,
                    maxlength: 60
                },
                username: {
                    required: true,
                    check_username: true
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 16,
                    setpassword: true
                },
                repassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 16,
                    equalTo: "#password"
                }
            },
            messages: {
                email: {
                    required: emailEmpty,
                    email: notValidEmail,
                    maxlength: emailLen
                },
                username: {
                    required: usernameEmpty,
                    username: notValidUsername
                },
                password: {
                    required: passwordEmpty,
                    minlength: passwordLength,
                    maxlength: passwordLength,
                    setpassword: passwordFormat
                },
                repassword: {
                    required: againPassword,
                    minlength: passwordLength,
                    maxlength: passwordLength,
                    equalTo: twoPassword
                }
            },
            // the errorPlacement has to take the table layout into account
            errorPlacement: function(error, element) {
                if (element.is(":radio"))
                    error.appendTo(element.parent().next().next());
                else if (element.is(":checkbox"))
                    error.appendTo(element.next());
                else
                    error.appendTo(element.parent().next());
            },
            // set this class to error-labels to indicate valid fields
            success: function(label) {
                // set &nbsp; as text for IE
                label.html("&nbsp;").addClass("checked");
            },
            highlight: function(element, errorClass) {
                $(element).parent().next().find("." + errorClass).removeClass("checked");
            },
            onkeyup: false,
            focusCleanup: true,
            onfocusout: function(element, event) {
                validator.element(element);
            },
            showErrors: function(errorMap, errorList) {
                this.defaultShowErrors();
            },
            debug: true
        });
    }
    //检测邮箱是否存在函数
function checkEmailExist(email, is_exist) {
        var emailExist = $("#email_exist").val();
        var directlyLogin = $("#directly_login").val();
        var remote_url = '';
        if (email == '') {
            return false;
        }
        if (is_exist == 1) {
            remote_url = '/jsapi/auth/is_email_notexist';
        } else {
            remote_url = '/jsapi/auth/is_email_exist';
        }
        $.ajax({
            type: "get",
            dataType: "json",
            url: remote_url,
            data: "email=" + email,
            success: function(data) {
                if (!data) {
                    var err_msg = '';
                    err_msg = emailExist + "<a href='/sso/signin?signin_email=" + $('#email').val() + "' style=\"color:#2c8beb;font-size:12px\">" + directlyLogin + "</a>";
                    $('#email').parent().next('td').find('.error').show();
                    $('#email').parents('tr').find('.error').removeClass('checked');
                    if ($('#email').parents('tr').find('.error').length > 0) {
                        $('#email').parents('tr').find('.error').html(err_msg);
                    } else {
                        $('#email').parents('tr').find('.tip').after('<label for="email" class="error">' + err_msg + '</label>');
                    }
                    return false;
                } else {
                    if ($('#email').parents('tr').find('.error').html().length == 0) {
                        $('#email').parents('tr').find('.error').addClass("checked");
                    }
                }
            }
        });
    }
    //lsm add on 2015/5/29
function checkUsernameExist(username) {
    // var username=$('#username').val();
    var usernameExist = $("#username_exists").val();
    var directlyLogin = $("#directly_login").val();
    var remote_url = '';
    if (username == '') {
        return false;
    }
    remote_url = '/jsapi/auth/is_username';
    $.ajax({
        type: "get",
        dataType: "json",
        url: remote_url,
        data: "username=" + username,
        success: function(data) {
            if (!data) {
                $('#username').parent().next('td').find('.error').show();
                $('#username').parents('tr').find('.error').removeClass('checked');
                if ($('#username').parents('tr').find('.error').length > 0) {
                    $('#username').parents('tr').find('.error').html(usernameExist);
                } else {
                    $('#username').parents('tr').find('.tip').after('<label for="email" class="error">' + usernameExist + '</label>');
                }
                return false;
            } else {
                if ($('#username').parents('tr').find('.error').html().length == 0) {
                    $('#username').parents('tr').find('.error').addClass("checked");
                }
            }
        }
    });
}

function checkRegisterForm() {
    $("#regsub").attr('disabled', true);
    var options = {
        url: "/jsapi/auth/do_signup",
        type: 'post',
        success: callbackRegister,
        dataType: 'json'
    };
    $('#registerform').ajaxSubmit(options);
}

function callbackRegister(responseText, statusText) {
        if (statusText == 'success') {
            $.each(responseText, function(i, obj) {
                $('#' + i).parents('tr').find('.tip').hide();
                if (i != 'signup') {
                    //表单验证
                    if (!obj.status) {
                        $('#' + i).parents('tr').find('.error').removeClass('checked');
                        if ($('#' + i).parents('tr').find('.error').length > 0) {
                            $('#' + i).parents('tr').find('.error').html(obj.err);

                        } else {
                            $('#' + i).parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                        }
                    }
                } else {
                    //提交成功
                    if (obj.status == true) {
                        send_validate_mail_url = $("#link_send_email").val();
                        window.location.href = send_validate_mail_url;
                    } else {
                        $('#regsub').parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                    }
                }
            });
        }
        $("#regsub").val($('#signup').val());
    }
    //检测用户名或者邮箱是否存在
function checkAccountExist() {
        var enterAccount = $("#enter_account").val();
        var accountNoExits = $("#account_no_exits").val();
        var registerNow = $("#register_now").val();
        var account = $('#account').val();
        var remote_url = '';
        if ($('#account').val() == '') {
            $('#account').parent().next('td').find('.error').show();
            $('#account').parents('tr').find('.error').removeClass('checked');
            if ($('#account').parents('tr').find('.error').length > 0) {
                $('#account').parents('tr').find('.error').html(enterAccount);

            } else {

                $('#account').parents('tr').find('.tip').after('<label for="account" class="error">' + enterAccount + '</label>');
            }
            return false;

        }
        remote_url = '/jsapi/auth/is_account_exist';
        $.ajax({
            type: "get",
            dataType: "json",
            url: remote_url,
            data: "account=" + account,
            success: function(data) {
                if (data.status == 'false') {
                    var err_msg = accountNoExits + "<a href='" + data.data + "' style=\"color:#2c8beb;font-size:12px\">" + registerNow + "</a>";
                    $('#account').parent().next('td').find('.error').show();
                    $('#account').parents('tr').find('.error').removeClass('checked');
                    if ($('#account').parents('tr').find('.error').length > 0) {
                        $('#account').parents('tr').find('.error').html(err_msg);
                    } else {
                        $('#account').parents('tr').find('.tip').after('<label for="account" class="error">' + err_msg + '</label>');
                    }
                    return false;
                } else {
                    $('#account').parent().next('td').find('.error').show();
                    if ($('#account').parents('tr').find('.error').length > 0) {
                        $('#account').parents('tr').find('.error').html('').addClass("checked");
                    } else {
                        $('#account').parents('tr').find('.tip').after('<label for="account" class="error checked"></label>');

                    }
                }
            }
        });
    }
    //忘记密码验证
function checkForm() {
    if ($('#forgetpassform .error:not(.checked)').length > 0) {
        return false;
    }
}

function callbackForgetPass(responseText, statusText) {
        if (statusText == 'success') {
            $('.error').remove();
            var error_msg;

            $.each(responseText, function(i, obj) {

                if (i != 'forget_pass') {

                    if (!obj.status) {

                        if ($('#' + i).parents('tr').find('.error').length > 0) {
                            $('#' + i).parents('tr').find('.error').removeClass("checked").html(obj.err);
                        } else {
                            $('#' + i).parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                        }
                    }
                } else {
                    if (obj.status == true) {
                        //show_notice_url = $("#forget_pass_link").val()+obj.data;
                        show_notice_url = $("#forget_pass_link").val() + obj.sign;
                        window.location.href = show_notice_url;
                    } else {
                        if (obj.err == 'try_forget_pass_too_many_times') {
                            error_msg = obj.data;

                        } else {
                            error_msg = obj.err;
                        }
                        if ($('#account').parents('tr').find('.error').length > 0) {
                            $('#account').parents('tr').find('.error').removeClass("checked").html(error_msg);
                        } else {
                            $('#account').parents('tr').find('.tip').after('<label for="account" class="error">' + error_msg + '</label>');
                        }
                    }
                }
            });
        }
    }
    //重置密码验证
function checkResetPForm() {
    if ($('#resetpassform .error:not(.checked)').length > 0) {
        $('.error').show();
        return false;
    }
}

function callbackResetPass(responseText, statusText) {
        if (statusText == 'success') {
            $.each(responseText, function(i, obj) {
                if (i != 'reset_pass') {
                    //表单验证
                    if (!obj.status) {
                        $('#' + i).parents('tr').find('.error').removeClass('checked');
                        if ($('#' + i).parents('tr').find('.error').length > 0) {
                            $('#' + i).parents('tr').find('.error').html(obj.err);
                        } else {
                            $('#' + i).parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                        }
                    }
                } else {
                    //提交成功
                    if (obj.status == true) {
                        show_notice_url = $("#reset_pass_link").val() + obj.data;
                        window.location.href = show_notice_url;
                    } else {
                        if ($('#restsub').parents('tr').find('.error').length > 0) {
                            $('#restsub').parents('tr').find('.error').html(obj.err);
                        } else {
                            $('#restsub').parents('tr').find('.tip').after('<label for="' + i + '" class="error">' + obj.err + '</label>');
                        }
                    }
                }
            });
        }
    }
    //重置密码前端验证
function checkResetPassFront() {
    var passwordEmpty = $("#password_empty").val();
    var passwordLength = $("#password_length").val();
    var passwordFormat = $("#password_format").val();
    var againPassword = $("#again_password").val();
    var twoPassword = $("#two_password").val();
    var passwordLen = $("#pwd_char").val();
    var validator = $("#resetpassform").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 16,
                setpassword: true
            },
            repassword: {
                required: true,
                minlength: 6,
                maxlength: 16,
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: passwordEmpty,
                minlength: passwordLength,
                maxlength: passwordLength,
                setpassword: passwordFormat
            },
            repassword: {
                required: againPassword,
                minlength: passwordLen,
                maxlength: passwordLen,
                equalTo: twoPassword
            }
        },
        // the errorPlacement has to take the table layout into account
        errorPlacement: function(error, element) {
            if (element.is(":radio"))
                error.appendTo(element.parent().next().next());
            else if (element.is(":checkbox"))
                error.appendTo(element.next());
            else
                error.appendTo(element.parent().next());
        },
        // set this class to error-labels to indicate valid fields
        success: function(label) {
            // set &nbsp; as text for IE
            label.html("&nbsp;").addClass("checked");
        },
        highlight: function(element, errorClass) {
            $(element).parent().next().find("." + errorClass).removeClass("checked");
        },
        onkeyup: false,
        focusCleanup: true,
        onfocusout: function(element, event) {
            validator.element(element);
        },
        showErrors: function(errorMap, errorList) {
            this.defaultShowErrors();
        },
        debug: true
    });
}

function isChecked(id) {
    var isChecked = true;
    $.each($('#' + id + ' input'), function(i, obj) {
        obj = $(obj);
        if (obj.val() == '') {
            isChecked = false;
            return false;
        } else if (obj.val() != '') {
            if (obj.parent("td").siblings("td").children("label").hasClass("error") && !(obj.parent("td").siblings("td").children("label").hasClass("checked"))) {
                isChecked = false;
                return false;
            }
        }

    });
    return isChecked;
}
$(function() {
    /*登录页面验证开始*/
    //登录用户名和密码前端验证 
    checkUserPassword();
    //有验证框的话，前端验证用checkVode()函数，在id=vcode input中onblur事件中绑定;
    $(document).on('focus', '#loginform input', function() {
        $('#loginsub').attr('disabled', false);
    });
    $(document).on('focus', '#vcode', function() {
        $('#vcode').attr('data-key', 'true');
    });
    $("#loginsub").on("click", function(e) {
        $("#loginsub").val($('#login_in_process').val());
        var subcheck = checkLForm();
        if (subcheck != true) {
            $("#loginsub").val($('#log_in').val());
            return false;
        }
        e.preventDefault();
        $("#loginsub").attr('disabled', true);
        var options = {
            url: "/sso/do_signin",
            type: 'post',
            success: callback,
            dataType: 'json'
        };
        $('#loginform').ajaxSubmit(options);
    });
    /*注册页面验证开始*/
    $('#registerform input').focus(function() {
        $('#regsub').attr('disabled', false);
    })

    checkRegisterFront();

    $(document).on('blur', '#email', function() {
        checkEmailExist($('#email').val(), 1);
    });
    $(document).on('blur', '#username', function() {
        checkUsernameExist($('#username').val());
    });

    $("input,textarea").focus(function() {
        if ($(this).val() == '') {
            if ($(this).parents('tr').find('.error').length > 0) {
                $(this).parents('tr').find('.error').hide();
            }
            $(this).parents('tr').find('.tip').show();
        }
    }).blur(function() {
        $(this).parents('tr').find('.tip').hide();
    });
    $("#regsub").on("click", function(e) {
        var tips = $('#signup_in_process').val();
        $("#regsub").val(tips);
        $("#regsub").attr('disabled', true);
        if (isChecked("registerform")) {
            e.preventDefault();
            var options = {
                url: "/jsapi/auth/do_signup",
                type: 'post',
                success: callbackRegister,
                dataType: 'json'
            };
            $('#registerform').ajaxSubmit(options);
        }
        else{
           $("#regsub").val($('#signup').val());
           return false;
        }

    });
    /*找回密码页面验证开始*/
    $('#forgetpassform input').focus(function() {
        $('#forgetsub').attr('disabled', false);
    });
    $("#forgetsub").on("click", function(e) {
        $("#forgetsub").attr('disabled', true);
        e.preventDefault();
        var options = {
            url: "/jsapi/auth/do_forget_pass",
            type: 'post',
            beforeSubmit: checkForm,
            success: callbackForgetPass,
            dataType: 'json'
        };
        $('#forgetpassform').ajaxSubmit(options);
    });
    /*重置密码页面验证开始*/
    $("#resetpassform input[type='text'],#resetpassform input[type='password']").focus(function() {
        $('#restsub').attr('disabled', false);
    });
    checkResetPassFront();
    $("#restsub").on("click", function(e) {
        $("#resetpassform").attr('disabled', true);
        e.preventDefault();
        var options = {
            url: "/jsapi/auth/do_reset_pass",
            type: 'post',
            beforeSubmit: checkResetPForm,
            success: callbackResetPass,
            dataType: 'json'
        };
        $('#resetpassform').ajaxSubmit(options);
    });
    chooseLanguage();
});

function chooseLanguage() {
    $("#changeLanguage").change(function() {
        var baseURL = window.location.href.split("?")[0];
        var value = $(this).val();
        if ($(this).attr("data-oauth") == "1") {
            window.location.href = baseURL + $(this).attr("data-href") + "&lang=" + value;
        } else {
            window.location.href = baseURL + "?lang=" + value;
        }
    });

}
