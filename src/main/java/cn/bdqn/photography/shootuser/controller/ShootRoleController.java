package cn.bdqn.photography.shootuser.controller;


import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private IShootUserService iShootUserService;

    //编辑个人资料页面
    @RequestMapping(value = "/personalInfo")
    public String personalInfo(Model model){
        Subject subject=SecurityUtils.getSubject();
        ShootUser user = (ShootUser)subject.getSession().getAttribute("user");
        ShootUser shootUser = iShootUserService.personageByUserCode(user.getUserCode());
        model.addAttribute("dizhi",shootUser.getShootAddress().getShootProw().getProw()+
                shootUser.getShootAddress().getShootCity().getCity());  //地址
        model.addAttribute("tuxiang","/images/"+user.getPortyaitl());
        return "personage/personalInfo";
    }

    //积分签到页面
    @RequestMapping(value = "/integral")
    public String integral(){
        return "personage/integral";
    }

}
