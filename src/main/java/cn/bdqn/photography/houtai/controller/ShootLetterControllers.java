package cn.bdqn.photography.houtai.controller;
import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/ShootLetter")
public class ShootLetterControllers {
    @Resource
    private IShootLetterService letterService;
    //查询所有私信
    @RequestMapping("/like")
    public String select(HttpServletRequest ht){
        List<ShootLetter> shootLetter = (List<ShootLetter>) letterService.list();
        ht.setAttribute("info",shootLetter);
        return "houtai/article-letter";
    }
}
