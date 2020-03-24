package cn.bdqn.photography.shootuser.controller;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.config.AliPayConfig;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import cn.bdqn.photography.shoottheme.service.IShootThemeService;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import cn.bdqn.photography.utils.IsPath;
import cn.bdqn.photography.utils.Round;
import cn.bdqn.photography.utils.Sex;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenzi.sms.ZhenziSmsClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Controller
@RequestMapping("/shoot-user")
public class ShootUserController {
    //获得当前用户 Subject当前用户 全局subject
    Subject subject = null;

    @Autowired
    @Qualifier("isPath")
    private IsPath isPath;

    @Autowired
    @Qualifier("shootUserService")
    private IShootUserService iShootUserService;

    @Autowired
    private IShootInfoService iShootInfoService;

    @Autowired
    private IShootImagesService iShootImagesService;

    @Autowired
    private IShootThemeService iShootThemeService;

    @Autowired
    private IShootOrderService iShootOrderService;

    //主页
    @RequestMapping(value = {"/index"})
    public String index(Model model, @RequestParam(value = "current", defaultValue = "0", required = false)
            int current, @RequestParam(value = "city", required = false) String city
            , @RequestParam(value = "costId", required = false) Long costId,
                        @RequestParam(value = "roleId", required = false) Long roleId,
                        @RequestParam(value = "sex", required = false) Long sex) {
        if (city != "" && city != null) {
            city = city + "市";
        }
        //约拍信息查询
        IPage<ShootInfo> page = iShootInfoService.findInfo(1l, city, costId, roleId, sex, current);

        for (ShootInfo info1 : page.getRecords()) {

            //设置用户图片路劲
            info1.getShootUser().setPortyaitl("/images/" + info1.getShootUser().getPortyaitl());

            Map<String, Object> map = new HashMap<>();
            map.put("infoId", info1.getId());
            //根据id查找信息
            Collection<ShootImages> shootImages = iShootImagesService.listByMap(map);
            if (shootImages != null && shootImages.size() > 0) {
                for (ShootImages images : shootImages
                ) {
                    //设置info图片路劲
                    images.setImagesName("/images/" + images.getImagesName());
                }
                info1.setShootImages((List<ShootImages>) shootImages);  //放入info字段中
            }
        }

        model.addAttribute("info", page.getRecords());     //数据
        model.addAttribute("current", page.getCurrent());  //当前页
        model.addAttribute("pages", page.getPages());      //总页数
        model.addAttribute("total", page.getTotal());      //总条数

        return "index/index";
    }

    //到登录页面
    @RequestMapping(value = {"/login"})
    public String logo(@RequestParam(value = "info", required = false)
                               String info, @RequestParam(value = "userCode", required = false)
                               String userCode, Model model) {
        model.addAttribute("info", info);   //存放注册成功信息
        model.addAttribute("userCode", userCode);  //userCode编码
        return "login";
    }

    //登录
    @RequestMapping(value = {"/sbuLogin"})
    public String login(ShootUser user, @RequestParam(value = "rememberMe", required = false)
            boolean rememberMe, Model model) {
        //传入用户编码 密码 将会去验证和认证授权
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserCode(), user.getUserPassword());
        //创建当前用户
        subject = SecurityUtils.getSubject();
        subject.logout();   //退出登录
        //主体提交登录请求到SecurityManager
        token.setRememberMe(rememberMe);
        try {
            //成功则会发放令牌  执行登录认证操作
            subject.login(token);
            System.out.println("token:" + token.getPassword());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，密码不正确！");
            model.addAttribute("info", "密码不正确");
            ice.printStackTrace();
        } catch (UnknownAccountException uae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，未知账户！");
            model.addAttribute("info", "未知账户");
        } catch (LockedAccountException lae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，账户锁定！");
            lae.printStackTrace();
        } catch (ExcessiveAttemptsException eae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，错误次数太多！");
            eae.printStackTrace();
        } catch (AuthenticationException ae) {
            System.out.println("对用户【" + user.getUserCode() + "】进行登录验证，验证未通过，用户名,未知错误!");
            ae.printStackTrace();
        }
        if (subject.isAuthenticated()) {
            System.out.println("登陆成功！！！");
            return "redirect:index";
        } else {
            System.out.println("登录失败！！！");
            token.clear();
            return "login";
        }
    }

    //到resgiter注册页面
    @RequestMapping(value = {"/register"})
    public String register() {
        return "register";
    }

    //注册
    @RequestMapping(value = {"/add"})
    public String add(ShootUser user, @RequestParam(value = "sexs", required = false) String sexs,
                      ShootProw prow, ShootCity city, ShootCountry country, ShootUserRole userRole,
                      @RequestParam(value = "protyaitl", required = false) MultipartFile[] multipartFile,
                      HttpServletRequest request, HttpSession session,
                      RedirectAttributes attributes, Model model) {
        user.setSex(Sex.transition(sexs)); //性别转换
        session.setAttribute("temp", "user");
        String[] img = isPath.upload(multipartFile, request, session);  //获得上传文件名称
        user.setPortyaitl(img[0]);  //因为只有一个头像所以是下标为0
        ShootAddress address = new ShootAddress();
        boolean insert = iShootUserService.saveUser(user, prow, city, country, userRole);
        if (insert == true) {
            //重定向 可以自动把参数拼接到url地址上
            attributes.addAttribute("info", "注册成功请输入密码登录");
            attributes.addAttribute("userCode", user.getUserCode());  //传递userCode到登录页
            return "redirect:login";
        } else {
            model.addAttribute("info", insert);
            return "register";
        }
    }

    //userCode验证是否存在
    @GetMapping(value = "/userByUserCode/{userCode}")
    @ResponseBody
    public Boolean userByUserCode(@PathVariable("userCode") String userCode) {
        System.out.println("userCode:" + userCode);
        QueryWrapper<ShootUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userCode", userCode);
        ShootUser one = iShootUserService.getOne(queryWrapper);
        if (one != null) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    //约拍信息详情
    @RequestMapping(value = {"/about"})
    public String about() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return "index/about";
    }

    //我的 个人中心 主页
    @RequestMapping(value = "/personage")
    public String personage(Model model) {
        ShootUser user = (ShootUser) subject.getSession().getAttribute("user");
        ShootUser shootUser = iShootUserService.personageByUserCode(user.getUserCode());
        model.addAttribute("dizhi", shootUser.getShootAddress().getShootProw().getProw() +
                shootUser.getShootAddress().getShootCity().getCity());  //地址
        model.addAttribute("juese", shootUser.getRoles().get(0).getRoleName());  //角色
        model.addAttribute("member", shootUser.getMember());  //是否有会员
        return "personage/personage";
    }

    //个人中心到发布信息页
    @RequestMapping(value = "/postMessage")
    public String postMessage(@RequestParam(value = "themName", defaultValue = "发布信息")
                                      String themName, Model model) {

        //有主题就查询主题id传过去
        if (!themName.equals("发布信息")) {
            QueryWrapper<ShootTheme> queryWrapper = new QueryWrapper<>();
            System.out.println("theme:" + themName);
            queryWrapper.eq("themName", themName);
            ShootTheme one = iShootThemeService.getOne(queryWrapper);
            model.addAttribute("id", one.getId());
        }

        model.addAttribute("themName", themName);
        return "personage/postMessage";
    }

    @RequestMapping("/ses")
    public void ses() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    //绑定手机页
    @RequestMapping(value = "/personagePhone")
    public String personagePhone() {
        return "personage/personagePhone";
    }

    //短信平台相关参数
    private String apiUrl = "https://sms_developer.zhenzikj.com";  //平台地址
    private String appId = "104923";   //id
    private String appSecret = "d45c2fce-ed04-4ba2-9a7e-670e038374f1";  //实例

    //绑定手机号 获取验证码 成功后 添加到用户中
    @RequestMapping(value = "/phone")
    @ResponseBody
    public Object addPhone(@RequestParam(value = "phone", required = false)
                                   String phone, HttpSession session) {

        try {
            JSONObject json = null;
            //随机生成验证码
            String code = String.valueOf(new Random().nextInt(999999));
            //将验证码通过榛子云接口发送至手机
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            String result = client.send(phone, "您的验证码为:" + code + "，该码有效期为5分钟，该码只能使用一次!");
            json = JSONObject.parseObject(result);  //装换为object json格式
            if (json.getIntValue("code") != 0) {//发送短信失败
                return false;
            }
            //将验证码存到session中,同时存入创建时间
            //以json存放，这里使用的是阿里的fastjson
            json = new JSONObject();
            json.put("phone", phone);
            json.put("code", code);
            json.put("createTime", System.currentTimeMillis());
            session.setAttribute("code", json);  //放入session中
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //到达手机登录页面
    @RequestMapping(value = "/registerPhone")
    public String registerPhone() {
        return "registerPhone";
    }

    //更改字段 增加值
    @RequestMapping(value = "/updatePhone")
    public String updatePhone(@RequestParam(value = "phone", required = false)
                                      String phone, Model model) {

        ShootUser user = (ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        user.setPhone(phone);
        boolean b = iShootUserService.updateById(user);
        if (b == true) {
            return "redirect:/shoot-role/personalInfo";
        } else {
            model.addAttribute("message", "绑定手机号失败");
            return "personage/personalInfo";
        }

    }


    //根据手机号码查询数据
    @RequestMapping(value = "/byPhone")
    @ResponseBody
    public ShootUser byPhone(@RequestParam(value = "phone", required = false)
                                     String phone) {
        QueryWrapper<ShootUser> query = new QueryWrapper<>();
        query.eq("phone", phone);
        ShootUser one = iShootUserService.getOne(query);
        return one;
    }

    //加入会员页面
    @RequestMapping(value = "/joinMember")
    public String joinMember() {
        return "personage/joinMember";
    }

    //选择支付方式页面
    @RequestMapping(value = "/payment")
    public String payment(@RequestParam(value = "price", required = false)
                                  Float price,
            @RequestParam(value = "subject",required = false)
            String subject, Model model) {
        String qixian;
        if (price == 50) {
            qixian = "月";
        } else {
            qixian = "年";
        }
        model.addAttribute("subject",subject);  //商品名称
        model.addAttribute("price", price);     //商品价格
        model.addAttribute("qixian", qixian);   //月 或 年
        return "personage/payment";
    }


    Logger logger = LoggerFactory.getLogger("PayController.class");

    //访问支付宝页面 支付页面
    @RequestMapping(value = "/alipay/toPay")
    @ResponseBody
    public void alipay(@RequestParam(value = "amount",required = false)
                                   String amount,@RequestParam(value = "subject",required = false)
                         String subject,@RequestParam(value = "body",required = false)
                         String body,HttpSession session,
                         HttpServletResponse response, HttpServletRequest request) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.return_url);
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url);

        //生成订单号类
        Round round=new Round();
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = round.round();
        //付款金额，必填
        String total_amount =amount;

        //信息存放订单实体类
        ShootOrder order=new ShootOrder();
        order.setOutTradeNo(out_trade_no);
        order.setTotalAmount(Float.valueOf(total_amount));
        order.setSubject(subject);
        order.setBody(body);
        ShootUser user =(ShootUser) SecurityUtils.getSubject().getSession().getAttribute("user");
        order.setUserId(user.getId());
        session.setAttribute("order",order);

        //订单名称，必填
        //String subject ="蒋蒋集团";
        //商品描述，可空
        //String body ="好吃的好玩的";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AliPayConfig.charset);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }


    //支付宝异步通知
    @GetMapping("/alipay/notify_url")
    @ResponseBody
    public String notifyAlipay() {
        System.out.println("");
        logger.info("----notify-----");
        return " a li pay notify ";
    }

    //支付成功之后回调函数
    @GetMapping("/alipay/return_url")
    public String returnAlipay(HttpSession session) {
        Session session1 = SecurityUtils.getSubject().getSession();
        ShootOrder order =(ShootOrder) session.getAttribute("order");
        order.setCreationDate(LocalDateTime.now());   //存入当前生成订单时间
        boolean save = iShootOrderService.save(order);
        logger.info("----return-----");
        if(save){   //成功
            ShootUser user = (ShootUser)session1.getAttribute("user");
            LocalDate localDate=null;
            if(order.getTotalAmount()==50){   //办理 月会员
                if(user.getMember()==1){  //已是会员 续费
                    localDate = user.getMemberDate().plusMonths(1);
                }else {  //不是会员
                    localDate=LocalDate.now().plusMonths(1);
                }
            }else if(order.getTotalAmount()==300){  //办理年会员
                if(user.getMember()==1){  //已是会员 续费
                    localDate = user.getMemberDate().plusYears(1);
                }else {  //不是会员
                    localDate=LocalDate.now().plusYears(1);
                }
            }
            iShootUserService.modifyMember(order.getUserId(),localDate);
            session.removeAttribute("order");   //删除订单信息
            List<ShootUser> userByUserCode = iShootUserService.findUserByUserCode(user.getUserCode());
            session1.setAttribute("user",userByUserCode.get(0));   //更改之后重新查询当前user信息
            return "redirect:/shoot-user/personage";  //重工回到个人主页
        }else {
            return "personage/joinMember";            //失败则回到充值页
        }
    }


    //提高信用等级页面
    @RequestMapping(value = "/credit")
    public String credit(){
        return "personage/credit";
    }

}

