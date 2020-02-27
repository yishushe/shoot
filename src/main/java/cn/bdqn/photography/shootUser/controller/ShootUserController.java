package cn.bdqn.photography.shootUser.controller;


import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootUser.entity.ShootAddress;
import cn.bdqn.photography.shootUser.entity.ShootRole;
import cn.bdqn.photography.shootUser.entity.ShootUser;
import cn.bdqn.photography.shootUser.entity.ShootUserRole;
import cn.bdqn.photography.shootUser.service.IShootUserRoleService;
import cn.bdqn.photography.shootUser.service.IShootUserService;
import cn.bdqn.photography.utils.IsPath;
import cn.bdqn.photography.utils.Sex;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.catalina.util.Strftime;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-user")
public class ShootUserController {

    @Autowired
    @Qualifier("isPath")
    private IsPath isPath;

    @Autowired
    @Qualifier("shootUserService")
    private IShootUserService iShootUserService;

    @GetMapping(value = {"/index"})
    public String index(@RequestParam(value = "info",required = false)
                                    String info,Model model){
        model.addAttribute("info",info);
        return "index/index";
    }

    @RequestMapping(value = {"/login"})
    public String logo( ){
        return "login";
    }

    @RequestMapping(value = {"/sbuLogin"})
    public String login(ShootUser user,@RequestParam(value = "rememberMe",required = false)
            boolean rememberMe){
        //传入用户编码 密码 将会去验证和认证授权
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserCode(),user.getUserPassword());

        //获得当前用户 Subject当前用户
        Subject subject = SecurityUtils.getSubject();
        //主体提交登录请求到SecurityManager
        token.setRememberMe(rememberMe);
        try{
            //成功则会发放令牌
            subject.login(token);
            System.out.println("token:"+token.getPassword());
        }catch (IncorrectCredentialsException ice){
            System.out.println( "对用户【" + user.getUserName() +"】进行登录验证，验证未通过，密码不正确！");
            ice.printStackTrace();
        }catch (UnknownAccountException uae){
            System.out.println("对用户【" + user.getUserName() +"】进行登录验证，验证未通过，未知账户！");
        }catch (LockedAccountException lae){
            System.out.println("对用户【" + user.getUserName() +"】进行登录验证，验证未通过，账户锁定！");
            lae.printStackTrace();
        }catch (ExcessiveAttemptsException eae){
            System.out.println("对用户【" + user.getUserName() +"】进行登录验证，验证未通过，错误次数太多！");
            eae.printStackTrace();
        }catch (AuthenticationException ae){
            System.out.println("对用户【" + user.getUserName() +"】进行登录验证，验证未通过，用户名,未知错误!");
            ae.printStackTrace();
        }
        if(subject.isAuthenticated()){
            System.out.println("登陆成功！！！");
            return "redirect:index";
        }else{
            System.out.println("登录失败！！！");
            token.clear();
            return "login";
        }
    }

    @RequestMapping(value = {"/register"})
    public String register(){
        return "register";
    }

    @RequestMapping(value = {"/add"})
    public String add(ShootUser user, @RequestParam(value = "sexs",required = false) String sexs ,
                      ShootProw prow, ShootCity city, ShootCountry country, ShootUserRole userRole,
                      @RequestParam(value = "protyaitl",required = false)MultipartFile multipartFile,
                      HttpServletRequest request, HttpSession session,
                      RedirectAttributes attributes, Model model){
        user.setSex(Sex.transition(sexs)); //性别转换
        session.setAttribute("temp","user");
        String img=isPath.upload(multipartFile, request,session);  //获得上传文件名称
        user.setPortyaitl(img);
        ShootAddress address=new ShootAddress();
        boolean insert = iShootUserService.saveUser(user, address, prow, city, country,userRole);
        if(insert==true){
            //重定向 可以自动把参数拼接到url地址上
            attributes.addAttribute("info",insert);
            return "redirect:index";
        }else {
            model.addAttribute("info",insert);
            return "register";
        }
    }

    @RequestMapping(value = {"/about"})
    public String about(){
        return "index/about";
    }

}