package cn.bdqn.photography.shootuser.mapper;

import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
public interface ShootUserMapper extends BaseMapper<ShootUser> {

    int insertUser(ShootUser user);  //注册账户

    List<ShootUser> loginByUserCode(@Param("userCode") String userCode);  //登录

    ShootUser personageByUserCode(@Param("userCode") String userCode);  //个人信息


}
