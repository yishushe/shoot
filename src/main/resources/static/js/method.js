
var prov = $("#prow");
var city = $('#city');
var country = $('#country');

/*用于保存当前所选的省市区*/
var current = {
    prov: '',
    city: '',
    country: ''
};

window.onload=function () {
    var len = provice.length;
    for (var i = 0; i < len; i++) {
        var provOpt = document.createElement("option");
        provOpt.innerText = provice[i]['name'];
        provOpt.value = i;
        prov.append(provOpt);
    }
}


/*根据所选的省份来显示城市列表*/
function showCity(obj) {
    //city.options.length=provice[val]["city"].length;
    var val = obj.options[obj.selectedIndex].value;
    if (val != current.prov) {
        current.prov = val;
    }
    //console.log(val);
    if (val != null) {
        city.length = provice[val]["city"].length;
        var cityLen = provice[val]["city"].length;
        for (var j = 0; j < cityLen; j++) {
            var cityOpt = document.createElement('option');
            cityOpt.innerText = provice[val]["city"][j].name;
            cityOpt.value = j;
            city.append(cityOpt);
        }
    }

}

/*根据所选的城市来显示县区列表*/
function showCountry(obj) {
    var val = obj.options[obj.selectedIndex].value;
    current.city = val;
    if (val != null) {
        country.length = 1; //清空之前的内容只留第一个默认选项
        var countryLen = provice[current.prov]["city"][val].districtAndCounty.length;
       /* if (countryLen == 0) {
            addrShow.value = provice[current.prov].name + '-' + provice[current.prov]["city"][current.city].name;
            return;
        }*/

        for (var n = 0; n < countryLen; n++) {
            var countryOpt = document.createElement('option');
            countryOpt.innerText = provice[current.prov]["city"][val].districtAndCounty[n];
            countryOpt.value = n;
            country.append(countryOpt);
        }
    }
}