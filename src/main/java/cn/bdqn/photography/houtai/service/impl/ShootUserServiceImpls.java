package cn.bdqn.photography.houtai.service.impl;

import cn.bdqn.photography.houtai.entity.ShootUsers;
import cn.bdqn.photography.houtai.mapper.ShootUserMappers;
import cn.bdqn.photography.houtai.service.IShootUserServices;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Service("shootUserServices")
@Transactional
public class ShootUserServiceImpls extends ServiceImpl<ShootUserMappers, ShootUsers> implements IShootUserServices {
}