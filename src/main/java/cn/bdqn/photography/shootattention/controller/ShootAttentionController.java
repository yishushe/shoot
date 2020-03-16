package cn.bdqn.photography.shootattention.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot_attention/shoot-attention")
public class ShootAttentionController {

    @RequestMapping("/love")
    public String add(){
        return "houtai/article-list";
    }

    @RequestMapping("/like")
    public String la(){
        return "houtai/admin-role";
    }

    @RequestMapping("/lui")
    public String lo(){
        return "houtai/admin-role-add";
    }

    @RequestMapping("/lik")
    public String li(){
        return "houtai/member-list";
    }

   /* @RequestMapping("/like")
    public String lk(){
        return "houtai/admin-role";
    }*/



}
