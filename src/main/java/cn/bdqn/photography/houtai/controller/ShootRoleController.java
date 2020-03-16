package cn.bdqn.photography.houtai.controller;


import cn.bdqn.photography.houtai.entity.ShootRole;
import cn.bdqn.photography.houtai.entity.ShootUser;
import cn.bdqn.photography.houtai.entity.ShootUserRole;
import cn.bdqn.photography.houtai.service.IShootRoleService;
import cn.bdqn.photography.houtai.service.IShootUserRoleService;
import cn.bdqn.photography.houtai.service.IShootUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-houtai-role")
public class ShootRoleController {

}
