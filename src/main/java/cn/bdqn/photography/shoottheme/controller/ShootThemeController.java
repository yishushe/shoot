package cn.bdqn.photography.shoottheme.controller;


import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
import cn.bdqn.photography.shootselfie.service.IShootSelfieService;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import cn.bdqn.photography.shoottheme.service.IShootThemeService;
import cn.bdqn.photography.shootuser.entity.ShootRole;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/shoot-theme")
public class ShootThemeController {

    @Autowired
    private IShootInfoService iShootInfoService;
    @Autowired
    private IShootThemeService iShootThemeService;
    @Autowired
    private IShootImagesService iShootImagesService;
    @Autowired
    private IShootLetterService iShootLetterService;

    @Autowired
    private IShootSelfieService iShootSelfieService;
    
    //自拍页
    @RequestMapping("/zipai")
    public String zipai(Model model)
    {
        List<ShootSelfie> selfieLIst = iShootSelfieService.findSelfieLIst();
        for (ShootSelfie selfie:selfieLIst
             ) {
             selfie.setImagesName("/images/"+selfie.getImagesName());
             selfie.getShootUser().setPortyaitl("/images/"+selfie.getShootUser().getPortyaitl());
        }
        model.addAttribute("selfie",selfieLIst);
        return "selfie/zipai";
    }

    //主题页
    @RequestMapping("/top")
    public String topic(){
        return "selfie/topic";
    }

    //关注页
    @RequestMapping("/att")
    public String att(){
        return "selfie/attention";
    }

     //分页查询用户主题 主题页信息查询
    @RequestMapping("/topic2")
    public String topic2(@RequestParam(value = "id",required = false) String id, Model model,
                         @RequestParam(value = "city",required = false)
                         String city,@RequestParam(value = "current",defaultValue = "0",required = false)
                         int current){
        Long idd=Long.parseLong(id);

        //查询主题名称
       ShootTheme st= iShootThemeService.getById(idd);

       model.addAttribute("st",st);
       if(st!=null){
           List<ShootInfo> sif= iShootThemeService.selebythemeid(st);
           model.addAttribute("st",st);
           model.addAttribute("sif",sif);
       }

        if(city!="" && city!=null){
            city=city+"市";
        }

        //约拍信息查询
        IPage<ShootInfo> page= iShootThemeService.findInfoByThemeId(idd,city,current);

        for (ShootInfo info1 : page.getRecords()){

            //设置用户图片路劲
            info1.getShootUser().setPortyaitl("/images/"+info1.getShootUser().getPortyaitl());

            Map<String,Object> map=new HashMap<>();
            map.put("infoId",info1.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if(shootImages!=null && shootImages.size()>0){
                for (ShootImages images: shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/"+images.getImagesName());
                }
                info1.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
        }

        model.addAttribute("info",page.getRecords());     //数据
        model.addAttribute("current",page.getCurrent());  //当前页
        model.addAttribute("pages",page.getPages());      //总页数
        model.addAttribute("total",page.getTotal());      //总条数
        return "selfie/topic2";
    }

}
