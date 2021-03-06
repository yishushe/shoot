package cn.bdqn.photography.shootuser.service;

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
public interface IShootUserRoleService extends IService<ShootUserRole> {
    List<ShootUserRole> selebyuid(ShootUser s);
}
