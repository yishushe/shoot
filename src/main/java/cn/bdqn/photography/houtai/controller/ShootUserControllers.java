package cn.bdqn.photography.houtai.controller;
import cn.bdqn.photography.shootuser.entity.ShootRole;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.service.IShootUserRoleService;
import cn.bdqn.photography.shootuser.service.IShootUserService;
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

    //查询所有
    @RequestMapping("/love")
    public String All(HttpServletRequest ht){
        List<ShootUser> usersList = iShootUserService.list();
        ht.setAttribute("info",usersList);
        return "houtai/admin-user";
    }
    @RequestMapping("/admin-user-add")
    public String html(){
        return "houtai/admin-user-add";
    }

    //根据id获取
    @RequestMapping("/updateUser")
    public String updateUser(ShootUser id,Model model){
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("users",users);
        return "houtai/admin-user-add";
    }
    //更新
    @RequestMapping("/userUpdate")
    public  String userUpdate(ShootUser user){
        boolean shootUser= iShootUserService.updateById(user);

        if (shootUser==true){
            return "redirect:love?id="+user.getId();
        }else {
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
        List<ShootUser> usersList = (List<ShootUser>) iShootUserService.getUsersAll();
        ht.setAttribute("info",usersList);
        return "houtai/admin-role";
    }



    //修改角色信息
    @RequestMapping("/updateUsers")
    public String updateUsers(Model model,ShootUserRole id){
        ShootUserRole shootUserRole = iShootUserRoleService.getById(id);
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("user",shootUserRole);
        model.addAttribute("users",users);
        return "houtai/admin-role-add";
    }
    //更新信息
    @RequestMapping("/usersUpdate")
    public  String usersUpdate(ShootUser user,long RoleId ,long userId,long rolesId){
        ShootUserRole role = new ShootUserRole();
        role.setId(RoleId);
        role.setUserId(userId);
        role.setRoleId(rolesId);
        System.out.println(rolesId);
        iShootUserRoleService.updateById(role);

        boolean shootUser= iShootUserService.updateById(user);
        System.out.println(user.getUserName());
        if (shootUser==true){
            return "redirect:select?id="+user.getId();
        }else {
            return "houtai/admin-role-add";
        }
    }

}