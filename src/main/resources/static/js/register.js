/**
 * 表单提交时验证
 * @returns {boolean}
 */
function checkForm() {
    var Form = document.getElementById("formId");
    var bool = true;
    if (!InputUsernameBlur()){ alert("dd1");bool = false;}
    if (!InputPasswordBlur()){ alert("dd2");bool = false;}
    if (!InputRepasswordBlur()){ alert("dd3");bool = false;}
    if (!InputPhoneBlur()) { alert("dd4");bool = false;}
    if(!InputPortyaitlBlur()){ alert("dd5");bool = false;}
    if(!selectProw()){ alert("dd6");bool = false;}
    if(!selectCity()){ alert("dd7");bool = false;}
    if(!selectCountry()) { alert("dd8");bool = false;}
    if (bool==true) {
        Form.submit();
    }
    return bool;
}

/**
 * 用户名输入框失去焦点
 */
function InputUsernameBlur() {
    var uname = document.getElementById("InputUsername");
    var ename = document.getElementById("errorName");
    
    /* 用户名为空/不为空 */
    if (uname.value=="") {
        ename.innerHTML="用户名不能为空";
        return false;
    }
    else {
        ename.innerHTML="";
    }
    /* 密码长度 */
    if (uname.value.length<4 || uname.value.length>16) {
        ename.innerHTML="长度为4-16。";
        return false;
    }
    else {
        ename.innerHTML="";
    }
    return true;
}

/**
 * 密码输入框失去焦点
 */
function InputPasswordBlur() {
    var pwd = document.getElementById("InputPassword");
    var epwd = document.getElementById("errorPassword");
    /* 密码为空/不为空 */
    if (pwd.value=="") {
        epwd.innerHTML="密码不为空"
        return false;
    }
    else {
        epwd.innerHTML="";
    }
    /* 密码长度 */
    if (pwd.value.length<6 || pwd.value.length>16) {
        epwd.innerHTML="长度为6-16。"
        return false;
    }
    else {
        epwd.innerHTML="";
    }
    return true;
}

/**
 * 确认密码输入框失去焦点
 */
function InputRepasswordBlur() {
    var rpwd = document.getElementById("InputRepassword");
    var erpwd = document.getElementById("errorRepassword");
    /* 确认密码不为空 */
    if (rpwd.value=="") {
        erpwd.innerHTML="确认密码不为空"
        return false;
    }
    else {
        erpwd.innerHTML="";
    }
    /* 确认密码与密码不一致 */
    var pwd = document.getElementById("InputPassword");
    if (pwd.value != rpwd.value) {
        erpwd.innerHTML="密码不一致。"
        return false;
    }
    else {
        erpwd.innerHTML="";
    }
    return true;
}

/**
 * 手机输入框失去焦点
 */
function InputPhoneBlur() {
    var phone = document.getElementById("InputPhone");
    var error = document.getElementById("errorPhone");
    /* 邮箱不为空 */
    if (phone.value=="") {
        error.innerHTML="手机不能为空"
        return false;
    }
    else {
        error.innerHTML="";
    }
    return true;
}

/**
 * 身份验证
 */
function InputPortyaitlBlur() {
    var portyaitl=document.getElementById("InputPortyaitl");
    var index=portyaitl.selectedIndex;
    var errorPortyaitl=document.getElementById("errorPortyaitl");
    if(portyaitl.options[index].value=="0"){
        errorPortyaitl.innerText="青选择你的身份";
        return false;
    }else {
        errorPortyaitl.innerText="";
    }
    return true;
}

/**
 * 省级验证
 */
function selectProw() {
    var proText=document.getElementById("prow");
    var prow=document.getElementById("p");
    var index=proText.selectedIndex;
    var error=document.getElementById("error");
    if(proText.options[index].value==""){
        error.innerText="请选择省份";
        return false;
    }else {
        prow.value=proText.options[index].innerText;
        error.innerText="";
    }
    return true;
}

/**
 * 市级验证
 */
function selectCity() {
    var cityText=document.getElementById("city");
    var index=cityText.selectedIndex;
    var error=document.getElementById("error");
    var city=document.getElementById("c");
    if(cityText.options[index].value==""){
        error.innerText="请选择市";
        return false;
    }else {
        city.value=cityText.options[index].innerText;
        error.innerText="";
    }
    return true;
}

/**
 * 区级验证
 */
function selectCountry() {
    var countryText=document.getElementById("country");
    var error=document.getElementById("error");
    var country=document.getElementById("u");
    var index=countryText.selectedIndex;
    if(countryText.options[index].value==""){
        error.innerText="请选择地区";
        return false;
    }else {
        country.value=countryText.options[index].innerText;
        error.innerText="";
    }
    return true;
}