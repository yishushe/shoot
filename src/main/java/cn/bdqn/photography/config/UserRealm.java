package cn.bdqn.photography.config;

import cn.bdqn.photography.shootuser.entity.ShootPermission;
import cn.bdqn.photography.shootuser.entity.ShootRole;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.service.IShootUserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;


import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Qualifier("shootUserService")
    private IShootUserService iShootUserService;

    //先 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken toke) throws AuthenticationException {

        String userCode = (String)toke.getPrincipal();  //编码

        List<ShootUser> list=iShootUserService.findUserByUserCode(userCode);

        if(list.size()==0){  //未知账号
            throw new UnknownAccountException("没找到帐号！");    //没找到帐号
        }

        //获得当前用户 Subject当前用户
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("user",list.get(0));   //登录成功把user放入shiro用户sesssion中

        String pwd=list.get(0).getUserPassword();

        //对用户编码转换byte数组
        ByteSource salt = ByteSource.Util.bytes(userCode);

        //获取当前类的父类 类名
        String realName = this.getName();

        System.out.println("userPassword:"+pwd);

        //用户密码shiro会自己进行验证 验证不正确将会出现相应的错误
        ShootUser user=list.get(0);

        return new SimpleAuthenticationInfo(
                user,     //用户数据
                pwd,   //密码
                salt,         //加盐后的编码
                realName
        );

    }

    //再 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {
        //授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("add");
        System.out.println("授权");

        // 当前用户信息 从shiro数据源中获得数据 shiro间接的跟mysql数据源有关联 可以看作是mysql数据源
        ShootUser user = (ShootUser) principals.getPrimaryPrincipal();
        //user.setEmail("add");
        for (ShootRole role : user.getRoles()){
            System.out.println("roles:"+role.getRoleName());
            info.addRole(role.getRoleName());  //添加角色
            for (ShootPermission permission : role.getPermissions()){
                System.out.println("permission:"+permission.getPermissionName());
                info.addStringPermission(permission.getPermissionName());  //授权
            }
        }
        return info;
    }


}