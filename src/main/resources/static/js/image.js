//上传多张图片显示在页面
function readFile(){
    var dataArr = { data : [] };  //数组
    var fd = new FormData();  //formData类型
    var iLen = 1;  //本次上传文件数量
    if(iLen>9){
        iLen=1;
        alert("已添加到9张照片");
    }
    for(var i=0;i<iLen;i++){   //循环遍历
        alert(iLen);
        /*if (!input['value'].match(/.jpg|.gif|.png|.bmp/i)){　　//采用mathh判断所选文件格式
            return alert("上传的图片格式不正确，请重新选择");
        }*/
        alert("dddddd");
        iLen++;
        var reader = new FileReader();    //浏览对象
        fd.append(i,this.files[i]);         //添加第i个元素到fa
        reader.readAsDataURL(this.files[i]);  //转成base64 url
        var fileName = this.files[i].name;    //获取文件名称
        reader.onload = function(e){
            var imgMsg = {
                name : fileName,//获取文件名
                base64 : this.result   //reader.readAsDataURL方法执行完后，base64数据储存在reader.result里
            }
            dataArr.data.push(imgMsg);   //img 添加到数组里
            var result = '<div style="display:none" class="result" ><img src="'+this.result+'" alt=""/></div>';
            var div = document.createElement('div');
            div.innerHTML = result;
            div['className'] = 'float';
            document.getElementsByTagName('body')[0].appendChild(div);  　　//插入页面
            var img = div.getElementsByTagName('img')[0];
            alert(img);
            img.onload = function(){
                var nowHeight = ReSizePic(this); //设置图片大小
                this.parentNode.style.display = 'block';
                var oParent = this.parentNode;
                if(nowHeight){
                    oParent.style.paddingTop = (oParent.offsetHeight - nowHeight)/2 + 'px';
                }
            }

        }
    }
}

//图片大小处理1函数
function ReSizePic(ThisPic) {
    var RePicWidth = 200; //这里修改为您想显示的宽度值

    var TrueWidth = ThisPic.width; //图片实际宽度
    var TrueHeight = ThisPic.height; //图片实际高度

    if(TrueWidth>TrueHeight){
        //宽大于高
        var reWidth = RePicWidth;
        ThisPic.width = reWidth;
        //垂直居中
        var nowHeight = TrueHeight * (reWidth/TrueWidth);
        return nowHeight;  //将图片修改后的高度返回，供垂直居中用
    }else{
        //宽小于高
        var reHeight = RePicWidth;
        ThisPic.height = reHeight;
    }
}

