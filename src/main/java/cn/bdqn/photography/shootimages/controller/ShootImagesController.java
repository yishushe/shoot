package cn.bdqn.photography.shootimages.controller;


import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/shoot-images")
public class ShootImagesController {
    @Autowired
    private IShootImagesService iShootImagesService;
    @RequestMapping("/allimg")
    public String showallimg(Model model){
        List<ShootImages> list= iShootImagesService.imglist();
        for (ShootImages info1 : list){

            //设置用户图片路劲
            info1.setImagesName("/images/"+info1.getImagesName());

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
            }
        }
        model.addAttribute("img",list);
        return "personage/showimg";
    }
}
