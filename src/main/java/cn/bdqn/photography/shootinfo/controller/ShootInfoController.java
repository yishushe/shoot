package cn.bdqn.photography.shootinfo.controller;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-info")
public class ShootInfoController {

    @Autowired
    private IShootInfoService iShootInfoService;

    @Autowired
    private IShootImagesService iShootImagesService;

    //约拍信息添加
    @RequestMapping(value = "/addInfo")
    public String addInfo(@RequestParam("imagesNamg") MultipartFile[] multipartFiles,
                          HttpSession session, ShootInfo shootInfo, ShootImages shootImages,
                          HttpServletRequest request, ShootProw prow, ShootCity city, ShootCountry country,
                          @RequestParam(value = "styleName",required = false) String styleName){

        session.setAttribute("temp","info");  //存放文件夹地址选择器
        boolean b = iShootInfoService.addInfo(shootInfo, shootImages, session, multipartFiles, request,
                prow,city,country,styleName);
        if(b==true){  //添加约拍信息成功
           return "redirect:/shoot-user/personage";  //重定向到个人页
        }
        return "personage/postMessage";
    }


    //约拍详情页面
    @RequestMapping(value = "/about")
    //@ResponseBodys
    public String about(@RequestParam("id") Long id, Model model){

        ShootInfo infoById = iShootInfoService.findInfoById(id);

        //用户头像路劲设置
        infoById.getShootUser().setPortyaitl("/images/"+infoById.getShootUser().getPortyaitl());

        Map<String,Object> map=new HashMap<>();
        map.put("infoId",infoById.getId());
        //根据id查找信息
        Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
        if(shootImages!=null && shootImages.size()>0){
            for (ShootImages images: shootImages
            ) {
                //设置info图片路劲
                images.setImagesName("/images/"+images.getImagesName());
            }
            infoById.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
        }

        System.out.println(infoById.getShootInfoStyle());

        model.addAttribute("info",infoById);
        return "index/about";
    }

}
