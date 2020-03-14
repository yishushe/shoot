$(document).ready(function () {
    $(".login").parent().click(function () {
         var text = $("#p02").text();
         if (text=='' || text==null){  //没有登录回到login页面
             location.href="/shoot-user/login";
         }
    })
})