package cn.bdqn.photography.houtai.controller;

import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.service.IShootUserRoleService;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-houtai-user")
public class ShootUserControllers {
    @Resource
    private IShootUserService iShootUserService;
    @Resource
    private IShootUserRoleService iShootUserRoleService;

    @RequestMapping("/love")
    public String All(HttpServletRequest ht) {
        List<ShootUser> usersList = iShootUserService.list();
        ht.setAttribute("info", usersList);
        return "houtai/admin-user";
    }

    @RequestMapping("/admin-role-add")
    public String html() {
        return "houtai/admin-role-add";
    }


    //根据id获取
    @RequestMapping("/updateUser")
    public String updateUser(ShootUser id,Model model){
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("users",users);
        return "houtai/admin-user-add";
    }

    @RequestMapping("/userUpdate")
    public String userUpdate(ShootUser user) {
        boolean shootUser = iShootUserService.updateById(user);
        if (shootUser == true) {
            return "redirect:love";
        } else {
            return "houtai/admin-user-add";
        }
    }

    //添加
    @RequestMapping("/insterUser")
    public String insterUser() {
        return "houtai/article-list";
    }
    //添加
    @RequestMapping("/add")
    public String add(ShootUser user){
        boolean save = iShootUserService.save(user);
        if (save==true){
            return "redirect:love";
        }else {
            return "houtai/article-list";
        }
    }


    //查看用户角色信息
    @RequestMapping("/select")
    public  String selectAll(HttpServletRequest ht){
        List<ShootUser> usersList = iShootUserService.getUsersAll();
        ht.setAttribute("info",usersList);
        return "houtai/admin-role";
    }
    //修改角色信息
    @RequestMapping("/updateUsers")
    public String updateUsers(Model model, Long id){
        Long surid=iShootUserService.sesurid(id);
        ShootUserRole ss=new ShootUserRole();
        ss.setId(surid);
        ShootUserRole shootUserRole = iShootUserRoleService.getById(ss);
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("user",shootUserRole);
        model.addAttribute("users",users);
        return "houtai/admin-role-add";
    }
    //更新信息
    @RequestMapping("/usersUpdate")
    public  String usersUpdate(ShootUser user,Long RoleId ,Long userId,Long rolesId,Long usersid){
        System.out.println("uid"+usersid+"rid:"+rolesId);
        boolean uuu= iShootUserService.updr(usersid,rolesId);
        boolean shootUser= iShootUserService.updateById(user);
        System.out.println("是否更新成功"+shootUser+uuu);
        if (shootUser==true && uuu==true){
            return "redirect:select";
        }else {
            return "houtai/admin-role-add";
        }
        //return "redirect:select";
    }
}