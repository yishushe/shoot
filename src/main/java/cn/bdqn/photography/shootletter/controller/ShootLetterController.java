package cn.bdqn.photography.shootletter.controller;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootinform.entity.ShootInform;
import cn.bdqn.photography.shootinform.service.IShootInformService;
import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.utils.Round;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

    @Autowired
    private IShootOrderService iShootOrderService;

    @Autowired
    private IShootInformService iShootInformService;

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

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId(),0l);

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
    public String responseMessage(@RequestParam(value = "infoId",required = false)
            Long infoId,Model model){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId(),infoId);

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("size",letterByPutUserId.size());
        model.addAttribute("letter",letterByPutUserId);
        return "put/responseMessage";
    }

    //我收到的请求 默认查询全部 收到的请求 也是去到请求页面
    @RequestMapping(value = "/incomingrequests")
    public String incomingrequests(Model model){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserId(user.getId(),0l);

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }

        model.addAttribute("size",letterByPutUserId.size());
        model.addAttribute("letter",letterByPutUserId);
        return "put/incomingrequests";
    }


    //异部 查看全部收到请求
    @RequestMapping(value = "/all")
    @ResponseBody
    public Object[] all(@RequestParam(value = "costName",required = false)
                        String costName){
        ShootUser user = (ShootUser)SecurityUtils.getSubject().getSession().getAttribute("user");

        List<ShootLetter> letterByPutUserId = iShootLetterService.findLetterByPutUserIdPut(user.getId(),costName);

        for (ShootLetter letter: letterByPutUserId
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }
        Object[] objects = letterByPutUserId.toArray();
        return objects;
    }

    //接受 拒绝 详情页面
    @RequestMapping(value = "/accept/{id}/{infoId}")
    public String accept(@PathVariable("id") Long id,@PathVariable("infoId") Long infoId,
                         Model model){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        ShootLetter letterBySendUserIdAndPutUserId2 = iShootLetterService.findLetterBySendUserIdAndPutUserId2(id, user.getId(),infoId);
        letterBySendUserIdAndPutUserId2.getShootUser().setPortyaitl("/images/"+letterBySendUserIdAndPutUserId2.getShootUser().getPortyaitl());
        model.addAttribute("letter",letterBySendUserIdAndPutUserId2);
        return "put/accept";
    }


    //同意约拍请求
    @RequestMapping(value = "/consent")
    @ResponseBody
    public boolean consent(@RequestParam(value = "infoId",required = false)
                          Long infoId,@RequestParam(value = "stateId",required = false)
                          Long stateId){
        //接单成功更改状态
        int i = iShootInfoService.modifyStateIdById(infoId, stateId);
        if(i>0){
            return true;
        }
        return false;
    }

    //查看待执行约拍信息
    @RequestMapping(value = "/execute")
    @ResponseBody
    public Object[] execute(@RequestParam(value = "stateId",required = false)
                            Long stateId){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<ShootLetter> letterConste = iShootLetterService.findLetterConste(user.getId(), stateId);
        for (ShootLetter letter: letterConste
        ) {
            //设置头像路劲
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
            if(letter.getShootUser().getPhone()=="" || letter.getShootUser().getPhone()==null){
                letter.getShootUser().setPhone("暂时没有设置");
            }
        }
        return letterConste.toArray();
    }


    //完成 本次约拍
    @RequestMapping(value = "/complete")
    @ResponseBody
    public boolean complete(@RequestParam(value = "infoId",required = false)
                            Long infoId,@RequestParam(value = "stateId",required = false)
                            Long stateId,@RequestParam(value = "typeName",required = false)
                            String typeName,@RequestParam(value = "costName",required = false)
                            String costName,@RequestParam(value = "sendUserId",required = false)
                            Long sendUserId){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        Round round=new Round();
        ShootOrder order=new ShootOrder();
        order.setOutTradeNo(round.round());
        order.setSubject(typeName);
        order.setBody(costName);
        order.setSendUserId(sendUserId);
        order.setUserId(user.getId());
        order.setCreationDate(LocalDateTime.now());
        boolean save = iShootOrderService.save(order);
        int i = iShootInfoService.modifyStateIdById(infoId, stateId);  //更改约拍状态
        if(i>0 && save==true){  //更改成功 订单生成成功
              return true;
        }
        return false;
    }

    //查看已完成约拍
    @RequestMapping(value = "/stocks")
    @ResponseBody
    public Object[] stocks(Model model,@RequestParam(value = "stateId",required = false)
                           Long stateId){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<ShootLetter> orderByStateIdAndUserId = iShootLetterService.findOrderByStateIdAndUserId(stateId, user.getId());
        for (ShootLetter letter:orderByStateIdAndUserId){
            letter.getShootUser().setPortyaitl("/images/"+letter.getShootUser().getPortyaitl());
        }
        return orderByStateIdAndUserId.toArray();
    }


    //通知页面
    @RequestMapping(value = "/inform")
    public String inform(Model model){
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<ShootInform> list = iShootInformService.findByPutUserId(user.getId());
        model.addAttribute("size",list.size());
        model.addAttribute("inform",list);
        return "put/inform";
    }

}

