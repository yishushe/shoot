package cn.bdqn.photography.shootuser.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-role")
public class ShootRoleController {

    //编辑个人资料页面
    @RequestMapping(value = "/personalInfo")
    public String personalInfo(){
        return "personage/personalInfo";
    }

    //积分签到页面
    @RequestMapping(value = "/integral")
    public String integral(){
        return "personage/integral";
    }

}
