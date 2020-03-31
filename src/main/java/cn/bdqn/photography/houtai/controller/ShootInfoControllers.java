package cn.bdqn.photography.houtai.controller;

import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/shootstaticsh")
public class ShootInfoControllers {

    @Autowired
    private IShootInfoService iShootInfoService;
    @Autowired
    private IShootImagesService iShootImagesService;

    @RequestMapping("/shenhe")
    public String shenhe(@RequestParam(value = "id",required = false)Long id,@RequestParam(value = "current",required = false) int current, Model model){
        /*if(current==0){
            current=1;
        }*/
        IPage<ShootInfo> page=iShootInfoService.getInfoByStateId(current,id);
        for (ShootInfo info1 : page.getRecords()){
            //设置用户图片路劲
            info1.getShootUser().setPortyaitl("/images/"+info1.getShootUser().getPortyaitl());
            Map<String,Object> map=new HashMap<>();
            map.put("infoId",info1.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if(shootImages!=null && shootImages.size()>0){
                for (ShootImages images: shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/"+images.getImagesName());
                }
                info1.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
        }
        model.addAttribute("info",page.getRecords());     //数据
        model.addAttribute("idd",id);
        model.addAttribute("current",page.getCurrent());  //当前页
        model.addAttribute("pages",page.getPages());      //总页数
        model.addAttribute("total",page.getTotal());      //总条数
        return "houtai/shxx";
    }
    @RequestMapping("/upduserstate")
    public String updateuserstate(Long id,Model model){
        List<ShootInfo> shootInfo=iShootInfoService.getinfobyinfoid(id);
        String city=shootInfo.get(0).getShootAddress().getShootProw().getProw()+shootInfo.get(0).getShootAddress().getShootCity().getCity()+shootInfo.get(0).getShootAddress().getShootCountry().getCountry();
        for (ShootInfo info1 : shootInfo){

            //设置用户图片路劲
            info1.getShootUser().setPortyaitl("/images/"+info1.getShootUser().getPortyaitl());

            Map<String,Object> map=new HashMap<>();
            map.put("infoId",info1.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if(shootImages!=null && shootImages.size()>0){
                for (ShootImages images: shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/"+images.getImagesName());
                }
                info1.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
        }
        model.addAttribute("im",shootInfo);
        model.addAttribute("city",city);
        return "houtai/updstate";
    }
    @RequestMapping("/wupdatebyinfoid")
    public String updatebyinfoid(Long  aa,String sele){
        Long stateid=Long.parseLong(sele);
        ShootInfo s= iShootInfoService.findInfoMessageById(aa);
        s.setStateId(stateid);
        boolean b= iShootInfoService.updateById(s);
        if(b==true){
            return "redirect:shenhe?id=1&current=2";
        }else{
            return "houtai/sb";
        }
    }
}
