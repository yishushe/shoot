package cn.bdqn.photography.shootuser.controller;


import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import cn.bdqn.photography.utils.IsPath;
import cn.bdqn.photography.utils.Sex;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-user")
public class ShootUserController {

    //获得当前用户 Subject当前用户 全局subject
    Subject subject = null;

    @Autowired
    @Qualifier("isPath")
    private IsPath isPath;

    @Autowired
    @Qualifier("shootUserService")
    private IShootUserService iShootUserService;

    //主页
    @GetMapping(value = {"/index"})
    public String index(Model model) {
        return "index/index";
    }

    //到登录页面
    @RequestMapping(value = {"/login"})
    public String logo(@RequestParam(value = "info", required = false)
                               String info,@RequestParam(value = "userCode",required = false)
            String userCode, Model model) {
        model.addAttribute("info", info);   //存放注册成功信息
        model.addAttribute("userCode",userCode);  //userCode编码
        return "login";
    }

    //登录
    @RequestMapping(value = {"/sbuLogin"})
    public String login(ShootUser user, @RequestParam(value = "rememberMe", required = false)
            boolean rememberMe,Model model) {
        //传入用户编码 密码 将会去验证和认证授权
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserCode(), user.getUserPassword());
        //创建当前用户
        subject=SecurityUtils.getSubject();
        subject.logout();   //退出登录
        //主体提交登录请求到SecurityManager
        token.setRememberMe(rememberMe);
        try {
            //成功则会发放令牌  执行登录认证操作
            subject.login(token);
            System.out.println("token:" + token.getPassword());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，密码不正确！");
            model.addAttribute("info","密码不正确");
            ice.printStackTrace();
        } catch (UnknownAccountException uae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，未知账户！");
            model.addAttribute("info","未知账户");
        } catch (LockedAccountException lae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，账户锁定！");
            lae.printStackTrace();
        } catch (ExcessiveAttemptsException eae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，错误次数太多！");
            eae.printStackTrace();
        } catch (AuthenticationException ae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，用户名,未知错误!");
            ae.printStackTrace();
        }
        if (subject.isAuthenticated()) {
            System.out.println("登陆成功！！！");
            return "redirect:index";
        } else {
            System.out.println("登录失败！！！");
            token.clear();
            return "login";
        }
    }

    //到resgiter注册页面
    @RequestMapping(value = {"/register"})
    public String register() {
        return "register";
    }

    //注册
    @RequestMapping(value = {"/add"})
    public String add(ShootUser user, @RequestParam(value = "sexs", required = false) String sexs,
                      ShootProw prow, ShootCity city, ShootCountry country, ShootUserRole userRole,
                      @RequestParam(value = "protyaitl", required = false) MultipartFile[] multipartFile,
                      HttpServletRequest request, HttpSession session,
                      RedirectAttributes attributes, Model model) {
        user.setSex(Sex.transition(sexs)); //性别转换
        session.setAttribute("temp", "user");
        String[] img = isPath.upload(multipartFile, request, session);  //获得上传文件名称
        user.setPortyaitl(img[0]);  //因为只有一个头像所以是下标为0
        ShootAddress address = new ShootAddress();
        boolean insert = iShootUserService.saveUser(user, prow, city, country, userRole);
        if (insert == true) {
            //重定向 可以自动把参数拼接到url地址上
            attributes.addAttribute("info", "注册成功请输入密码登录");
            attributes.addAttribute("userCode",user.getUserCode());  //传递userCode到登录页
            return "redirect:login";
        } else {
            model.addAttribute("info", insert);
            return "register";
        }
    }

    //userCode验证是否存在
    @GetMapping(value = "/userByUserCode/{userCode}")
    @ResponseBody
    public Boolean userByUserCode(@PathVariable("userCode") String userCode) {
        System.out.println("userCode:" + userCode);
        QueryWrapper<ShootUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userCode", userCode);
        ShootUser one = iShootUserService.getOne(queryWrapper);
        if (one != null) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    //约拍信息详情
    @RequestMapping(value = {"/about"})
    public String about() {
        return "index/about";
    }

    //我的 个人中心 主页
    @RequestMapping(value = "/personage")
    public String personage(Model model){
        ShootUser user = (ShootUser)subject.getSession().getAttribute("user");
        ShootUser shootUser = iShootUserService.personageByUserCode(user.getUserCode());
        model.addAttribute("dizhi",shootUser.getShootAddress().getShootProw().getProw()+
                shootUser.getShootAddress().getShootCity().getCity());  //地址
        model.addAttribute("juese",shootUser.getRoles().get(0).getRoleName());  //角色
        model.addAttribute("member",shootUser.getMember());  //是否有会员
        return "personage/personage";
    }

    //个人中心到发布信息页
    @RequestMapping(value = "/postMessage")
    public String postMessage(){
        return "personage/postMessage";
    }


}