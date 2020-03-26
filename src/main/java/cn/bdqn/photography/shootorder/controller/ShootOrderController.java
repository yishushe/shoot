package cn.bdqn.photography.shootorder.controller;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public String showorder(int current,String code, Model m){
        IPage<ShootOrder> l= iShootOrderService.showallorder(current,code);
       System.out.println("大小aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+l.getRecords().size());
       m.addAttribute("li",l.getRecords());
        m.addAttribute("current",l.getCurrent());  //当前页
        m.addAttribute("pages",l.getPages());      //总页数
        m.addAttribute("total",l.getTotal());      //总条数
        return "order/showallorder";
    }
}
