package cn.bdqn.photography.shootuser.service;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
public interface IShootUserService extends IService<ShootUser> {

    boolean saveUser(ShootUser user,
                     ShootProw prow, ShootCity city, ShootCountry country,
                     ShootUserRole userRole);  //用户注册

    List<ShootUser> findUserByUserCode(String userCode);

    ShootUser personageByUserCode(String userCode);   //个人信息


    ShootUser findByUserId(Long id);   //查询个人信息

    int modifyMember(Long id,LocalDate memberDate);  //添加会员

    Page<ShootUser> getpermission(int current,Long id);  //查询权限

    Long seleid(Long id,Long pid);  //查询是否有此权限

    boolean updp(Long id,Long pid,Long qxid); //更改权限

    boolean ins(Long id,Long pid);//增加权限

    boolean del(Long id,Long rid);//根据权限名称删除权限

    /**
     * 根据 id 添加 保证金
     * @param id
     * @param securityMoney
     * @return
     */
    int modifySecurityMoney(Long id,Float securityMoney);


    /**
     * 根据userid 查询个人信息
     * @param userId
     * @return
     */
    List<ShootUser> findUserId(Long userId);

}