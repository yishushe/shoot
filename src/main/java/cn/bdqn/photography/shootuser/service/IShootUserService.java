package cn.bdqn.photography.shootuser.service;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

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
}