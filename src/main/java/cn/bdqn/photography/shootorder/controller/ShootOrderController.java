package cn.bdqn.photography.shootorder.controller;

import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
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
    //查看我的所有账单
    @RequestMapping(value = "/orderList")
    public String orderList(Model model){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        QueryWrapper<ShootOrder> query=new QueryWrapper<>();
        query.eq("userId",user.getId());
        query.orderByDesc("creationDate");
        List<ShootOrder> list = iShootOrderService.list(query);
        Float expend = iShootOrderService.expend(user.getId());  //总支出
        Float income = iShootOrderService.income(user.getId());  //总收入
        model.addAttribute("expend",expend);
        model.addAttribute("income",income);
        model.addAttribute("orderList",list);
        return "personage/orderList";
    }

}
