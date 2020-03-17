package cn.bdqn.photography.shootinfo.controller;


import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/shoot-state")
public class ShootStateController {

    @Autowired
    private IShootInfoService iShootInfoService;

    @Autowired
    private IShootImagesService iShootImagesService;

    //到我发布信息页面 并做查询我的约拍信息操作
    @RequestMapping(value = "/infoMessage")
    public String infoMessage(Model model){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");
        List<ShootInfo> infoByUserId = iShootInfoService.findInfoByUserId(user.getId());

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


        model.addAttribute("info",infoByUserId);
        return "personage/infoMessage";
    }

}
