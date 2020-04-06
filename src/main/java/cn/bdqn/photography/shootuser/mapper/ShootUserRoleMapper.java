package cn.bdqn.photography.shootuser.mapper;

import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
public interface ShootUserRoleMapper extends BaseMapper<ShootUserRole> {
    List<ShootUserRole> selebyuid(ShootUser shootUser);
}
