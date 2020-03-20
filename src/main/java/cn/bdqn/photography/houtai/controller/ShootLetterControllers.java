package cn.bdqn.photography.houtai.controller;
import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

@Controller
@RequestMapping("/ShootLetter")
public class ShootLetterControllers {
    @Resource
    private IShootLetterService letterService;
    //查询所有私信
    @RequestMapping("/like")
    public String select(Model model){
        QueryWrapper<ShootLetter> wrapper = new QueryWrapper();
        wrapper.select();
        Page<ShootLetter> page1 = new Page<>(1,5);
        IPage<ShootLetter> iPage = letterService.selectPage(page1,wrapper);
        model.addAttribute("info",iPage.getRecords());     //数据
        model.addAttribute("current",iPage.getCurrent());   //当前页
        model.addAttribute("pages",iPage.getPages());      //总页数
        model.addAttribute("total",iPage.getTotal());      //总条数
        return "houtai/article-letter";
    }
}
