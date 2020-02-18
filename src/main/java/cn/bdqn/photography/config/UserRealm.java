package cn.bdqn.photography.config;

import cn.bdqn.photography.shootUser.entity.ShootPermission;
import cn.bdqn.photography.shootUser.entity.ShootRole;
import cn.bdqn.photography.shootUser.entity.ShootUser;
import cn.bdqn.photography.shootUser.mapper.ShootUserMapper;
import cn.bdqn.photography.shootUser.service.IShootUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.ir.RuntimeNode;

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
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sun.security.krb5.internal.ccache.CredentialsCache;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Qualifier("shootUserService")
    private IShootUserService iShootUserService;


    //先 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken toke) throws AuthenticationException {

        String userCode = (String)toke.getPrincipal();  //编码
        String credentials = new String((char[]) toke.getCredentials());   //密码
        List<ShootUser> list=iShootUserService.findUserByUserCode(userCode);
        System.out.println("list:"+list.size());
        if(list.size()==0){
            throw new UnknownAccountException("没找到帐号！");    //没找到帐号
        }

        //获得当前用户 Subject当前用户
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("user",list.get(0));   //登录成功把user放入shiro用户sesssion中

        Object principal = list.get(0).getUserCode();
        Object pwd=list.get(0).getUserPassword();

        ByteSource salt = ByteSource.Util.bytes(userCode); //对用户编码进行加盐

        String md5Pwd = new Md5Hash(credentials,salt).toHex();
        String realName = this.getName();

        System.out.println("userPassword:"+pwd);

        //用户密码shiro会自己进行验证 验证不正确将会出现相应的错误

        return new SimpleAuthenticationInfo(
                list.get(0),     //用户编码
                pwd,   //密码
                salt,         //对用编码进行加盐
                realName
        );

    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggg");
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