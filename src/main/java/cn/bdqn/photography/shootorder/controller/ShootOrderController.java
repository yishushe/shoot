package cn.bdqn.photography.shootorder.controller;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.bouncycastle.LICENSE;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/shoot-order")
public class ShootOrderController {
    @Autowired
    private IShootOrderService iShootOrderService;

    @RequestMapping("/allorder")
    public String showorder(String code, Model m,String xfje){
        String dyjl="";
        List<ShootOrder> l=null;
        if(xfje!=null && xfje.equals("当月消费金额查询")){
            l=iShootOrderService.showorderby();
            m.addAttribute("li",l);
            return "order/showallorder";
        }

        l= iShootOrderService.showallorder(code);
       System.out.println("大小:"+l.size());
       m.addAttribute("li",l);
        return "order/showallorder";
    }
}
