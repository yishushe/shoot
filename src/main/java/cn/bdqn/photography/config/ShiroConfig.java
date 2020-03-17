package cn.bdqn.photography.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
public class ShiroConfig {

    //Filter工厂，设置对应的过滤条件和跳转条件 ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShirFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        //shiro内置过滤器
        /*
        anon: 无需认证可以访问
        authc: 必须认证才能访问
        user: 必须拥有记住我才能访问
        perms: 拥有对某个资源的权限才能访问
        roles: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap=new LinkedHashMap<>(); //链式map 更好的增加和删除 查询则没有HasMap好数组方式好
        System.out.println("欢迎来到shiro安全世界！");
        //用户有add权限才可以执行的添加功能
        //filterMap.put("/user/addUser","perms[add]");
        filterMap.put("/shoot-user/logo","anon");   //登录页
        filterMap.put("/shoot-user/index","anon");  //主页
        filterMap.put("/shoot-user/register","anon");  //注册
        filterMap.put("/shoot-user/add","anon");      //添加操作
        filterMap.put("/shoot-user/subLogin","anon");   //登录操作
        filterMap.put("/shoot-user/personage","authc");  //个人中心主页
        filterMap.put("/shoot-role/personalInfo","authc");  //个人资料
        filterMap.put("/shoot-role/integral","authc");   //签到领取积分页面
        filterMap.put("/shoot-user/postMessage","authc");  //发布约拍信息页
        filterMap.put("/shoot-info/addInfo","authc");   //添加约拍信息操作
        filterMap.put("/shoot-info/about","anon");      //约拍详情页面
        filterMap.put("/shoot-letter/aboutMessage","authc");  //发起约拍详情页
        filterMap.put("/shoot-state/infoMessage","authc");    //我的约拍信息页
        filterMap.put("/shoot-letter/message","authc");  //查看别人给我发送的私信 留言页
        filterMap.put("/shoot-letter/replyMessage","authc");   //回复私信页面
        filterMap.put("/shoot-letter/requestMessage","authc");  //我发起的请求信息页

        filterMap.put("/shoot-user/about","perms[query]");

        //要有相应的角色和授权才能访问的页面
        //filterMap.put("/user/delete","roles[系统管理员],perms[delete]");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //如果没有认证到login页面
        filterFactoryBean.setLoginUrl("/shoot-user/login");
        //未授权
        //filterFactoryBean.setUnauthorizedUrl("/user/unauth");

        return filterFactoryBean;
    }

    //DefaultWebSecurityManager 创建管理对象  并注入userRealm
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联记住我
        securityManager.setRememberMeManager(rememberMeManager());
        //关联userRealm
        securityManager.setRealm(userRealm(matcher));
        return securityManager;
    }

    /**
     * cookie对象;
     * @return
     */
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie生效时间30天,单位秒;
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        // cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCipherKey("ZHANGXIAOHEI_CAT".getBytes());
        return cookieRememberMeManager;
    }


    //将自己的验证方式加入容器
    @Bean(name = "userRealm")
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        UserRealm authRealm = new UserRealm();
        //加入密码加密验证 比较器
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用MD5方式加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        //开启密码加密 true 开启  false 关闭
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 配置shiro跟spring的关联  类似切面
     * @param 
     * @return
     */
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("getDefaultWebSecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }*/


    //如果没有这两个配置，可能会授权失败，所以依赖中还需要配置aop的依赖
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(HashedCredentialsMatcher matcher) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getDefaultWebSecurityManager(matcher));
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }*/

    //整合shiroDialect 用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}