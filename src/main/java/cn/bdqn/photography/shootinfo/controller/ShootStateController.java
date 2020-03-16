package cn.bdqn.photography.shootinfo.controller;


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
@RequestMapping("/shoot-state")
public class ShootStateController {

    //到我发布信息页面
    @RequestMapping(value = "/infoMessage")
    public String infoMessage(){
        return "personage/infoMessage";
    }

}
