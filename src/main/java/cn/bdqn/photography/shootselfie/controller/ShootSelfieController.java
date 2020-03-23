package cn.bdqn.photography.shootselfie.controller;


import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
import cn.bdqn.photography.shootselfie.service.IShootSelfieService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.utils.IsPath;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-selfie")
public class ShootSelfieController {


    @Autowired
    private IShootSelfieService iShootSelfieService;

    @Autowired
    private IsPath isPath;

    //添加自拍页面
    @RequestMapping(value = "/selfie")
    public String selfie(){
        return "selfie/addSelfie";
    }

    //增加自拍
    @RequestMapping(value = "/addSelfie")
    public String addSelfie(@RequestParam(value = "imagesNamg")
                                        MultipartFile[] multipartFile,ShootSelfie selfie,  HttpServletRequest request,
                            HttpSession session){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        selfie.setUserId(user.getId());   //userId
        session.setAttribute("temp","common");
        System.out.println("multpartFile:"+multipartFile.length);
        String[] upload = isPath.upload(multipartFile, request, session);
        selfie.setImagesName(upload[0]);  //图片名称
        selfie.setCreationDate(LocalDateTime.now());  //当前时间
        boolean save = iShootSelfieService.save(selfie);
        if(save){
             return "redirect:/shoot-theme/zipai";
        }else {
             return "selfie/addSelfie";
        }
    }

}
