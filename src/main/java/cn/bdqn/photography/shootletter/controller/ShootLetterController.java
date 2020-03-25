package cn.bdqn.photography.shootletter.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/shoot-letter")
public class ShootLetterController {

    @Autowired
    private IShootLetterService iShootLetterService;

    @Autowired
    private IShootInfoService iShootInfoService;

    //发起约拍信息 留言添加
    @RequestMapping(value = "/addLetter")
    @ResponseBody
    public boolean addLetter(ShootLetter letter){
        Session session = SecurityUtils.getSubject().getSession();
        ShootUser user = (ShootUser)session.getAttribute("user");
        letter.setSendUserId(user.getId());   //放入发起人id
        letter.setCreationDate(LocalDateTime.now());  //当前时间
        boolean save = iShootLetterService.save(letter);
        return save;
    }

    //发起约拍留言 详情页
    @RequestMapping(value = "/aboutMessage")
    public String aboutMessage(@RequestParam(value = "infoId",required = false)
                                           Long infoId, Model model){

        ShootInfo message = iShootInfoService.findInfoMessageById(infoId);
        System.out.println("roleName:"+message.getShootUser().getRoles().get(0).getRoleName());
        model.addAttribute("message",message);

        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        model.addAttribute("touxiang","/images/"+user.getPortyaitl());

        QueryWrapper<ShootLetter> query=new QueryWrapper<>();
        query.eq("sendUserId",user.getId());
        query.eq("infoId",infoId);
        query.groupBy("sendUserId");
        query.orderByAsc("creationDate");  //降序排序
        ShootLetter one = iShootLetterService.getOne(query);
        model.addAttribute("letter",one);
        return "index/aboutMessage";
    }

    //私信页面
    @RequestMapping(value = "/message")
    public String message(Model model){

        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId());

        for (ShootLetter letter: letterByPutUserId
             ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("size",letterByPutUserId.size());
        model.addAttribute("letter",letterByPutUserId);
        return "put/letterMessage";
    }


    //回复私信信息页面
    @RequestMapping(value = "/replyMessage")
    public String replyMessage(@RequestParam(value = "sendUserId",required = false)
                               Long sendUserId,Model model){

        System.out.println("sendUserId:"+sendUserId);

        //把发起请求的人的id 放入session中 用户回复你的时候用这个id
        SecurityUtils.getSubject().getSession().setAttribute("sendUserId",sendUserId);

        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterBySnedUserIdAndPutUserId = iShootLetterService.findLetterBySnedUserIdAndPutUserId(sendUserId, user.getId());

        for (ShootLetter letter: letterBySnedUserIdAndPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("letter",letterBySnedUserIdAndPutUserId);
        model.addAttribute("infoId",letterBySnedUserIdAndPutUserId.get(0).getInfoId());
        return "put/replyMessage";
    }


    //回复信息添加
    @RequestMapping(value = "/addReplyMessage")
    @ResponseBody
    public boolean addReplyMessage(ShootLetter letter){
        letter.setCreationDate(LocalDateTime.now());
        //获取你查看发起人的id 你需要回复人家 所以你变成发起人 别人成为接受人
        letter.setPutUserId((Long) SecurityUtils.getSubject().getSession().getAttribute("sendUserId"));
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        letter.setSendUserId(user.getId());
        boolean save = iShootLetterService.save(letter);  //存入信息
        return save;
    }


    //我发送的约拍信息 页面
    @RequestMapping(value = "/requestMessage")
    public String requestMessage(Model model){

        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterBySendUserIdSend(user.getId());

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("letter",letterByPutUserId);
        return "put/requestMessage";
    }


    //我接收的约拍信息  页面
    @RequestMapping(value = "/responseMessage")
    public String responseMessage(Model model){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId());

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("size",letterByPutUserId.size());
        model.addAttribute("letter",letterByPutUserId);
        return "put/responseMessage";
    }

    //我收到的请求全部
    @RequestMapping(value = "/incomingrequests")
    public String incomingrequests(Model model){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId());

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("size",letterByPutUserId.size());
        model.addAttribute("letter",letterByPutUserId);
        return "put/incomingrequests";
    }

}
