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

    //查询所有
    @RequestMapping("/love")
    public String All(HttpServletRequest ht){
        List<ShootUser> usersList = iShootUserService.list();
        ht.setAttribute("info",usersList);
        return "houtai/admin-role";
    }
    @RequestMapping("/admin-role-add")
    public String html(){
        return "houtai/admin-role-add";
    }

    //根据id获取
    @RequestMapping("/updateUser")
    public String updateUser(ShootUser id,Model model){
        ShootUser users = iShootUserService.getById(id);
        model.addAttribute("users",users);
        return "houtai/admin-role-add";
    }
    //更新
    @RequestMapping("/userUpdate")
    public  String userUpdate(ShootUser user){
        boolean shootUser= iShootUserService.updateById(user);
        if (shootUser==true){
            return "redirect:love?id="+user.getId();
        }else {
            return "houtai/admin-role-add";
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

}