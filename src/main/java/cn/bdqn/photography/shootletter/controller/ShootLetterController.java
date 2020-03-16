package cn.bdqn.photography.shootletter.controller;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-letter")
public class ShootLetterController {

    @Autowired
    private IShootLetterService iShootLetterService;

    @Autowired
    private IShootInfoService iShootInfoService;

    //发起约拍信息 留言添加
    @RequestMapping(value = "/addLetter")
    @ResponseBody
    public boolean addLetter(ShootLetter letter){
        Session session = SecurityUtils.getSubject().getSession();
        ShootUser user = (ShootUser)session.getAttribute("user");
        letter.setSendUserID(user.getId());   //放入发起人id
        letter.setCreationDate(LocalDateTime.now());  //当前时间
        boolean save = iShootLetterService.save(letter);
        return save;
    }

    //发起约拍留言 详情页
    @RequestMapping(value = "/aboutMessage")
    public String aboutMessage(@RequestParam(value = "infoId",required = false)
                                           Long infoId, Model model){

        ShootInfo message = iShootInfoService.findInfoMessageById(infoId);
        System.out.println("roleName:"+message.getShootUser().getRoles().get(0).getRoleName());
        model.addAttribute("message",message);

        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        model.addAttribute("touxiang","/images/"+user.getPortyaitl());

        QueryWrapper<ShootLetter> query=new QueryWrapper<>();
        query.eq("sendUserId",user.getId());
        query.eq("infoId",infoId);
        ShootLetter one = iShootLetterService.getOne(query);
        model.addAttribute("letter",one);
        return "index/aboutMessage";
    }

}
