package cn.bdqn.photography.houtai.controller;

import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/houtai-ShootLetter")
public class ShootOrderControllers {
    @Autowired
    private IShootOrderService iShootOrderService;

    @RequestMapping("/allorder")
    public String showorder(String code, Model m, String xfje){
        String dyjl="";
        List<ShootOrder> l=null;
        if(xfje!=null && xfje.equals("当月消费金额查询")){
            l=iShootOrderService.showorderby();
            m.addAttribute("li",l);
            return "houtai/showallorder";
        }
        l= iShootOrderService.showallorder(code);
        m.addAttribute("li",l);
        return "houtai/showallorder";
    }
}
