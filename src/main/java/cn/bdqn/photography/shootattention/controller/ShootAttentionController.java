package cn.bdqn.photography.shootattention.controller;


import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootattention.service.IShootAttentionService;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-attention")
public class ShootAttentionController {

    @Autowired
    private IShootAttentionService iShootAttentionService;

    @Autowired
    private IShootImagesService iShootImagesService;

    @Autowired
    private IShootInfoService iShootInfoService;

    @Autowired
    private IShootUserService iShootUserService;

    //异步关注操作 用户操作
    @RequestMapping(value = "/addAttention")
    @ResponseBody
    public boolean addAttention(ShootAttention attention){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
         attention.setAttentionId(user.getId());  //关注者id
         attention.setCreationDate(LocalDateTime.now());  //关注事件
        boolean save = iShootAttentionService.save(attention);
        return save;
    }


    //异步取消关注 操作
    @RequestMapping(value = "/deleteAttention")
    @ResponseBody
    public boolean deleteAttention(ShootAttention attention){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        attention.setAttentionId(user.getId());   //被关注者id
        QueryWrapper<ShootAttention> query=new QueryWrapper<>();
        query.eq("attentionId",attention.getAttentionId());  //关注者
        query.eq("focusedId",attention.getFocusedId());      //被关注者id
        boolean remove = iShootAttentionService.remove(query);
        return remove;
    }


    //到达关注页面
    @RequestMapping(value = "/attention")
    public String attention(Model model){

        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        //查询头像
        List<ShootAttention> byAttentionId = iShootAttentionService.findByAttentionId(user.getId(),"我关注的人");
        //设置头像路劲
        if(byAttentionId!=null && byAttentionId.size()>0){
            for (ShootAttention attention: byAttentionId
                 ) {
               attention.getShootUser().setPortyaitl("/images/"+attention.getShootUser().getPortyaitl());
            }
        }

        //查询用户 约拍信息
        List<ShootInfo> byAttentionIdInfo = iShootAttentionService.findByAttentionIdInfo(user.getId());

        //循环设置图片访问路劲
        for (ShootInfo info: byAttentionIdInfo
             ) {
            Map<String,Object> map=new HashMap<>();
            map.put("infoId",info.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if(shootImages!=null && shootImages.size()>0){
                for (ShootImages images: shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/"+images.getImagesName());
                }
                info.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
            //设置头像路劲
            info.getShootUser().setPortyaitl("/images/"+info.getShootUser().getPortyaitl());
        }

        model.addAttribute("attentionTouxiang",byAttentionId);
        model.addAttribute("attentionInfo",byAttentionIdInfo);
        System.out.println("attentionInfo:"+byAttentionIdInfo.size());
        model.addAttribute("images","/images/photo_placeholder_300.png");
        return "attention/attention";
    }


    //被关注人的主页
    @RequestMapping(value = "/personalInfo")
    public String personalInfo(@RequestParam(value = "userId",required = false) Long userId, Model model){
        List<ShootInfo> infoByUserId = iShootInfoService.findInfoByUserId(userId);

        //循环设置图片路劲
        for (ShootInfo info : infoByUserId){
            Map<String,Object> map=new HashMap<>();
            map.put("infoId",info.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if(shootImages!=null && shootImages.size()>0){
                for (ShootImages images: shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/"+images.getImagesName());
                }
                info.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
        }

        model.addAttribute("touxiang","/images/"+infoByUserId.get(0).getShootUser().getPortyaitl());
        model.addAttribute("info",infoByUserId);
        return "attention/personalInfo";
    }

    //我关注的人页 查询我关注的用户 和关注我的人
    @RequestMapping(value = "/focuson")
    public String focuson(@RequestParam(value = "message",required = false)
            String message,Model model){

        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");

        //查看自己信息
        ShootUser byUserId = iShootUserService.findByUserId(user.getId());
        byUserId.setPortyaitl("/images/"+byUserId.getPortyaitl());  //头像路劲

        //我关注的人信息 和 关注我的人
        List<ShootAttention> byAttentionId = iShootAttentionService.findByAttentionId(user.getId(),message);
        //设置头像路劲
        if(byAttentionId!=null && byAttentionId.size()>0){
                for (ShootAttention attention: byAttentionId
                ) {
                    attention.getShootUser().setPortyaitl("/images/"+attention.getShootUser().getPortyaitl());
                }
            }

        model.addAttribute("byFocuson",byAttentionId);
        //是我关注的人 还 是关注我的人
        model.addAttribute("message",message);
        model.addAttribute("byUser",byUserId);

        return "attention/focuson";
    }

}
