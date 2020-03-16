package cn.bdqn.photography.houtai.service.impl;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.common.mapper.ShootCityMapper;
import cn.bdqn.photography.common.mapper.ShootCountryMapper;
import cn.bdqn.photography.common.mapper.ShootProwMapper;
import cn.bdqn.photography.houtai.entity.ShootUser;
import cn.bdqn.photography.houtai.entity.ShootUserRole;
import cn.bdqn.photography.houtai.mapper.ShootAddressMapper;
import cn.bdqn.photography.houtai.mapper.ShootUserMapper;
import cn.bdqn.photography.houtai.mapper.ShootUserRoleMapper;
import cn.bdqn.photography.houtai.service.IShootUserService;
import cn.bdqn.photography.utils.AddressUtls;
import cn.bdqn.photography.utils.Round;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Service("shootUserService")
@Transactional
public class ShootUserServiceImpl extends ServiceImpl<ShootUserMapper, ShootUser> implements IShootUserService {
}