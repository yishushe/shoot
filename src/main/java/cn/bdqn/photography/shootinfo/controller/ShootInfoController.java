package cn.bdqn.photography.shootinfo.controller;
import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootattention.service.IShootAttentionService;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
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
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/shoot-info")
public class ShootInfoController {

    @Autowired
    private IShootInfoService iShootInfoService;

    @Autowired
    private IShootImagesService iShootImagesService;

    @Autowired
    private IShootLetterService iShootLetterService;

    @Autowired
    private IShootAttentionService iShootAttentionService;

    //约拍信息添加
    @RequestMapping(value = "/addInfo")
    public String addInfo(@RequestParam("imagesNamg") MultipartFile[] multipartFiles,
                          HttpSession session, ShootInfo shootInfo, ShootImages shootImages,
                          HttpServletRequest request, ShootProw prow, ShootCity city, ShootCountry country,
                          @RequestParam(value = "styleName",required = false) String styleName){

        session.setAttribute("temp","info");  //存放文件夹地址选择器
        boolean b = iShootInfoService.addInfo(shootInfo, shootImages, session, multipartFiles, request,
                prow,city,country,styleName);
        if(b==true){  //添加约拍信息成功
           return "redirect:/shoot-user/personage";  //重定向到个人页
        }
        return "personage/postMessage";
    }


    //约拍详情页面
    @RequestMapping(value = "/about")
    //@ResponseBodys
    public String about(@RequestParam("id") Long id, Model model){

        Session session = SecurityUtils.getSubject().getSession();
        ShootUser user = (ShootUser)session.getAttribute("user");
        boolean flag=true;
        if(user!=null){
            QueryWrapper<ShootLetter> query=new QueryWrapper<>();
            query.eq("sendUserId",user.getId());
            query.eq("infoId",id);
            query.groupBy("sendUserId");
            query.orderBy(true,true,"creationDate");
            //查询是否 已经 给别人发送过约拍 留言
            ShootLetter letter = iShootLetterService.getOne(query);

            if(letter!=null){
                flag=false;
            }
        }

        ShootInfo infoById = iShootInfoService.findInfoById(id);


        //用户头像路劲设置
        infoById.getShootUser().setPortyaitl("/images/"+infoById.getShootUser().getPortyaitl());

        Map<String,Object> map=new HashMap<>();
        map.put("infoId",infoById.getId());
        //根据id查找信息
        Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
        if(shootImages!=null && shootImages.size()>0){
            for (ShootImages images: shootImages
            ) {
                //设置info图片路劲
                images.setImagesName("/images/"+images.getImagesName());
            }
            infoById.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
        }

        System.out.println("sssssssssssssssssssss:"+infoById.getRequireSecurityAccount());

        if(user!=null){
            //根据 关注者id 和 被关注者id 查询是否有数据
            QueryWrapper<ShootAttention> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("attentionId",user.getId());  //关注者 当前用户id
            queryWrapper.eq("focusedId",infoById.getShootUser().getId());  //被关注着 当前信息的id
            ShootAttention one = iShootAttentionService.getOne(queryWrapper);
            model.addAttribute("attentionId",one);    //传参到页面判断是否本条
        }

        model.addAttribute("stateId",infoById.getShootState().getId());
        model.addAttribute("info",infoById);
        model.addAttribute("flag",flag);
        return "index/about";
    }


    //约拍信息状态修改
    @RequestMapping(value = "/updateState")
    @ResponseBody
    public boolean updateState(@RequestParam(value = "id",required = false)
                              Long id,@RequestParam(value = "stateId",required = false)
                              Long stateId){
        int i = iShootInfoService.modifyStateIdById(id, stateId);
        if(i>0){   //更新成功
            return true;
        }else {   //失败
            return false;
        }
    }



}

