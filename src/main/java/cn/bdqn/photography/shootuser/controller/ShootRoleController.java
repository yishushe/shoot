package cn.bdqn.photography.shootuser.controller;


import cn.bdqn.photography.shootuser.entity.ShootRole;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.service.IShootRoleService;
import cn.bdqn.photography.shootuser.service.IShootUserRoleService;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    private IShootUserRoleService iShootUserRoleService;
    @Autowired
    private IShootRoleService iShootRoleService;

    public List<ShootUserRole> l(ShootUser s){
        return iShootUserRoleService.selebyuid(s);
    }
    //编辑个人资料页面
    @RequestMapping(value = "/personalInfo")
    public String personalInfo(Model model){
        Subject subject=SecurityUtils.getSubject();
        ShootUser user = (ShootUser)subject.getSession().getAttribute("user");
        ShootUser shootUser = iShootUserService.personageByUserCode(user.getUserCode());
        model.addAttribute("dizhi",shootUser.getShootAddress().getShootProw().getProw()+
                shootUser.getShootAddress().getShootCity().getCity());  //地址
        model.addAttribute("tuxiang","/images/"+user.getPortyaitl());
        List<ShootUser> list=iShootUserService.findUserByUserCode(all().getUserCode());
        Subject sub = SecurityUtils.getSubject();
        sub.getSession().setAttribute("user",list.get(0));   //更新完我的信息后 把session中的值更改掉
        ShootUser subb = (ShootUser)subject.getSession().getAttribute("user");
       List<ShootUserRole> s= iShootUserRoleService.selebyuid(subb);
        ShootRole ss=iShootRoleService.getById(s.get(0).getRoleId());
        return "personage/personalInfo";
    }

    //获取当前用户 sesssion
    public ShootUser all(){
        Subject subject = SecurityUtils.getSubject();
        ShootUser s=(ShootUser) subject.getSession().getAttribute("user");
        return s;
    }

    //积分签到页面
    @RequestMapping(value = "/integral")
    public String integral(HttpServletRequest h){
        System.out.println("签到的状态id"+all().getStateId());
        ShootUser uid= iShootUserService.getById(all().getId());
        h.setAttribute("integra",uid.getIntegral());
        h.setAttribute("zt",uid.getStateId());
        if(uid!=null){
            if(uid.getStateId()==8){
                h.setAttribute("yqd","您还未签到");
            }else{
                h.setAttribute("yqd","您已签到");
            }
        }
        return "personage/integral";
    }
    //积分签到更改数值
    @RequestMapping("/updintegral")
    public String updateintegral(HttpServletRequest h){
        all().setIntegral(all().getIntegral()+5);
        all().setStateId(Long.parseLong("9"));
        h.setAttribute("integra",all().getIntegral());
        h.setAttribute("zt",all().getStateId());
        boolean b= iShootUserService.updateById(all());
        if(b==true){
            return "redirect:integral";
        }else{
            return "houtai/sb";
        }

    }

    //更新个人信息
    @RequestMapping("/updateuser")
    public String updatebyuser(ShootUser shootUser,String label_select){

        ShootUserRole sur=new ShootUserRole();
        sur.setId(l(all()).get(0).getId());
        sur.setUserId(all().getId());
        int a = Integer.parseInt(label_select);
        sur.setRoleId((long) a);
        boolean b= iShootUserRoleService.updateById(sur);
        all().setSex(shootUser.getSex());
        all().setUserName(shootUser.getUserName());
        boolean b2=iShootUserService.updateById(all());
        System.out.println("b"+b);
        if(b==true && b2==true){
            return "redirect:/shoot-user/index";
        }else {
            return "personage/personalInfo";
        }
    }

    @RequestMapping("/abou")
    public String abo(){
        return "abouts/about";
    }
    @RequestMapping("/zhin")
    public String zhinan(){
        return "abouts/zhinan";
    }
    @RequestMapping("/kef")
    public String kefu(){
        return "abouts/kefu";
    }

}
