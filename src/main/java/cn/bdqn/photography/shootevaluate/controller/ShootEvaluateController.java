package cn.bdqn.photography.shootevaluate.controller;

import cn.bdqn.photography.shootevaluate.entity.ShootEvaluate;
import cn.bdqn.photography.shootevaluate.service.IShootEvaluateService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/shoot-evaluate")
public class ShootEvaluateController {

    @Autowired
    private IShootEvaluateService iShootEvaluateService;


    //添加评价
    @RequestMapping(value = "/addEvaluate")
    @ResponseBody
    public boolean addEvaluate(ShootEvaluate evaluate){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        evaluate.setSendUserId(user.getId());
        evaluate.setCreationDate(LocalDateTime.now());
        boolean save = iShootEvaluateService.save(evaluate);
        if(save){
            return true;
        }else {
            return false;
        }
    }


    //查看评价
    @RequestMapping(value = "/evaluateList")
    @ResponseBody
    public Object[] evaluateList(@RequestParam(value = "putUserId",required = false)
                                             Long putUserId){
        List<ShootEvaluate> listByPutUserId = iShootEvaluateService.findListByPutUserId(putUserId);
        //if(listByPutUserId!=null && listByPutUserId.size()>0){}
        List<ShootEvaluate> list=new ArrayList<>();
        for (ShootEvaluate evaluate : listByPutUserId){
            evaluate.getShootUser().setPortyaitl("/images/"+evaluate.getShootUser().getPortyaitl());
        }
        return listByPutUserId.toArray();
    }

}
