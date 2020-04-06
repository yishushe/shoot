package cn.bdqn.photography.houtai.controller;


import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.mapper.ShootUserMapper;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-permission")
public class ShootRolePermissionControllers {
    @Autowired
    private ShootUserMapper s;
    @Autowired
    private IShootUserService iShootUserService;

    @RequestMapping("/permission")
    public String permis(Long id, int current, Model model) {
        IPage<ShootUser> page = iShootUserService.getpermission(current, id);
        model.addAttribute("info", page.getRecords());     //数据
        model.addAttribute("current", page.getCurrent());  //当前页
        model.addAttribute("pages", page.getPages());      //总页数
        model.addAttribute("total", page.getTotal());      //总条数
        return "houtai/admin-permission";
    }

    @RequestMapping("/updper")
    public String updper(Long id, Model model) {
        IPage<ShootUser> page = iShootUserService.getpermission(1, id);
        model.addAttribute("info", page.getRecords());     //数据
        //Long js= s.seleid(id);
        //boolean b=s.updqx(js,id);
        //System.out.println(js);
        return "houtai/updpermission";
    }

    @RequestMapping("/ins")
    public String ins(Long sele, Long rid, String zzjj, String del, Long qxid, Long select, Long sele1, Long select2) {
        if (zzjj.equals("增加")) {
            Long b = iShootUserService.seleid(rid, sele);
            System.out.println("是否有此权限" + b);
            if (b == 0) {
                boolean bo = iShootUserService.ins(rid, sele);
                System.out.println("是否增加成功:" + bo);
                if (bo = true) {
                    return "redirect:permission?current=1";
                } else {
                    return "houtai/sb";
                }
            } else {
                return "houtai/sb";
            }
        }
        if (zzjj.equals("删除")) {
            Long b = iShootUserService.seleid(rid, select2);
            System.out.println("是否有此权限" + b + "选中的值" + select2);
            if (b != 0) {
                boolean bo = iShootUserService.del(select2, rid);
                System.out.println("是否删除成功:" + bo);
                if (bo = true) {
                    return "redirect:permission?current=1";
                } else {
                    return "houtai/sb";
                }
            }
        }
        if (zzjj.equals("修改")) {
            Long b1 = iShootUserService.seleid(rid, sele1);
            Long b2 = iShootUserService.seleid(rid, select);
            System.out.println(b1 + "aaaaaaaaaaaa" + b2);
            if (b1 == b2) {
                return "houtai/sb";
            } else {
                boolean bb = iShootUserService.updp(rid, sele1, select);
                System.out.println("是否修改成功:" + bb);
                if (bb == true) {
                    return "redirect:permission?current=1";
                } else {
                    return "houtai/sb";
                }
            }


        } else {
            return "houtai/sb";
        }
    }
}
