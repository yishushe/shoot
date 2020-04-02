package cn.bdqn.photography.houtai.controller;

import cn.bdqn.photography.shootuser.entity.ShootUser;
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

    @RequestMapping("/love")
    public String All(HttpServletRequest ht) {
        List<ShootUser> usersList = iShootUserService.list();
        ht.setAttribute("info", usersList);
        return "houtai/admin-role";
    }

    @RequestMapping("/admin-role-add")
    public String html() {
        return "houtai/admin-role-add";
    }


    @RequestMapping("/updateUser")
    public String updateUser(ShootUser id, Model model) {
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("users", users);
        return "houtai/admin-role-add";
    }

    @RequestMapping("/userUpdate")
    public String userUpdate(ShootUser user) {
        boolean shootUser = iShootUserService.updateById(user);
        if (shootUser == true) {
            return "redirect:love";
        } else {
            return "houtai/admin-role-add";
        }
    }
}