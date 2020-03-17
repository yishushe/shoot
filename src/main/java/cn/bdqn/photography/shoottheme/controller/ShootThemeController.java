package cn.bdqn.photography.shoottheme.controller;


import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import cn.bdqn.photography.shoottheme.service.IShootThemeService;
import cn.bdqn.photography.shootuser.entity.ShootRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-theme")
public class ShootThemeController {
    @Autowired
    private IShootInfoService iShootInfoService;
    @Autowired
    private IShootThemeService iShootThemeService;

    @RequestMapping("/zipai")
    public String zipai(){
        return "selfie/zipai";
    }
    @RequestMapping("/top")
    public String topic(){
        return "selfie/topic";
    }
    @RequestMapping("/att")
    public String att(){
        return "selfie/attention";
    }
    @RequestMapping("/topic2")
    public String topic2(String id, HttpServletRequest h){
       ShootTheme st= iShootThemeService.getById(id);
        List<ShootInfo> sif= iShootThemeService.selebythemeid(st);
       System.out.println("风格名字"+st.getThemName()+st.getId());
       System.out.println("用户约拍信息"+sif.get(0).getContent());
       h.setAttribute("st",st);
        h.setAttribute("sif",sif);
        return "selfie/topic2";
    }
}
