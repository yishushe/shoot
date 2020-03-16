package cn.bdqn.photography.houtai.controller;
import cn.bdqn.photography.houtai.mapper.ShootUserMappers;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-houtai-user")
public class ShootUserControllers {

    @Resource
    private ShootUserMappers shootUserMappers;

    //Increase one record
    @RequestMapping("/love")
    public int addRecord(ShootUser shootUser){
        return ;

    }
}