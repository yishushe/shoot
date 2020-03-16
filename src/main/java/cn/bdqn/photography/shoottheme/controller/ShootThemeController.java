package cn.bdqn.photography.shoottheme.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoottheme/shoot-theme")
public class ShootThemeController {
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
}
