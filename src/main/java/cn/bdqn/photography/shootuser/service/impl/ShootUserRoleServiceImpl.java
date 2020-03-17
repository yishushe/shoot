package cn.bdqn.photography.shootuser.service.impl;

import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.mapper.ShootUserRoleMapper;
import cn.bdqn.photography.shootuser.service.IShootUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Service
public class ShootUserRoleServiceImpl extends ServiceImpl<ShootUserRoleMapper, ShootUserRole> implements IShootUserRoleService {
    @Autowired
    private ShootUserRoleMapper shootUserRoleMapper;
    @Override
    public List<ShootUserRole> selebyuid(ShootUser shootUser) {
        return shootUserRoleMapper.selebyuid(shootUser);
    }
}