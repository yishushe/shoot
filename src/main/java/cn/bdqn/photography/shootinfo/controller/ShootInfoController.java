package cn.bdqn.photography.shootinfo.controller;


import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.utils.IsPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

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

}
