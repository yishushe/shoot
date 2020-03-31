package cn.bdqn.photography.shootselfie.controller;

import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootattention.service.IShootAttentionService;
import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
import cn.bdqn.photography.shootselfie.service.IShootSelfieService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.utils.IsPath;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/shoot-selfie")
public class ShootSelfieController {


    @Autowired
    private IShootSelfieService iShootSelfieService;

    @Autowired
    private IsPath isPath;

    @Autowired
    private IShootAttentionService iShootAttentionService;

    //添加自拍信息页面
    @RequestMapping(value = "/selfie")
    public String selfie(){
        return "selfie/addSelfie";
    }

    //增加自拍操作
    @RequestMapping(value = "/addSelfie")
    public String addSelfie(@RequestParam(value = "imagesNamg")
                                        MultipartFile[] multipartFile,ShootSelfie selfie,  HttpServletRequest request,
                            HttpSession session){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        selfie.setUserId(user.getId());   //userId
        session.setAttribute("temp","common");
        System.out.println("multpartFile:"+multipartFile.length);
        String[] upload = isPath.upload(multipartFile, request, session);
        selfie.setImagesName(upload[0]);  //图片名称
        selfie.setCreationDate(LocalDateTime.now());  //当前时间
        boolean save = iShootSelfieService.save(selfie);
        if(save){
             return "redirect:/shoot-theme/zipai";
        }else {
             return "selfie/addSelfie";
        }
    }


    //自拍详情展示页面
    @RequestMapping(value = "/selfieMessage")
    public String selfieMessage(@RequestParam(value = "id",required = false)
                                Long id, Model model){
        ShootSelfie byId = iShootSelfieService.findById(id);
        byId.getShootUser().setPortyaitl("/images/"+byId.getShootUser().getPortyaitl());
        byId.setImagesName("/images/"+byId.getImagesName());

        Session session = SecurityUtils.getSubject().getSession();
        ShootUser user = (ShootUser)session.getAttribute("user");

        //根据 关注者id 和 被关注者id 查询是否有数据
        QueryWrapper<ShootAttention> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("attentionId",user.getId());  //关注者 当前用户id
        queryWrapper.eq("focusedId",byId.getShootUser().getId());  //被关注着 当前信息的id
        ShootAttention one = iShootAttentionService.getOne(queryWrapper);
        model.addAttribute("attentionId",one);    //传参到页面判断是否本条

        model.addAttribute("selfie",byId);
        return "selfie/selfieMessage";
    }


}

