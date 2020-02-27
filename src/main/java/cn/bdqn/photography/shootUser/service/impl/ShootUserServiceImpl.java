package cn.bdqn.photography.shootUser.service.impl;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.common.mapper.ShootCityMapper;
import cn.bdqn.photography.common.mapper.ShootCountryMapper;
import cn.bdqn.photography.common.mapper.ShootProwMapper;
import cn.bdqn.photography.shootUser.entity.ShootAddress;
import cn.bdqn.photography.shootUser.entity.ShootUser;
import cn.bdqn.photography.shootUser.entity.ShootUserRole;
import cn.bdqn.photography.shootUser.mapper.ShootAddressMapper;
import cn.bdqn.photography.shootUser.mapper.ShootUserMapper;
import cn.bdqn.photography.shootUser.mapper.ShootUserRoleMapper;
import cn.bdqn.photography.shootUser.service.IShootUserService;
import cn.bdqn.photography.utils.Round;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Autowired
    @Qualifier("shootUserMapper")
    private ShootUserMapper shootUserMapper;

    @Autowired
    @Qualifier("shootProwMapper")
    private ShootProwMapper shootProwMapper;

    @Autowired
    @Qualifier("shootCityMapper")
    private ShootCityMapper shootCityMapper;

    @Autowired
    @Qualifier("shootCountryMapper")
    private ShootCountryMapper shootCountryMapper;

    @Autowired
    //@Qualifier("shootAddressMapper")
    private ShootAddressMapper shootAddressMapper;

    @Autowired
    @Qualifier("round")
    private Round round;

    @Autowired
    @Qualifier("shootUserRoleMapper")
    private ShootUserRoleMapper shootUserRoleMapper;

    /**
     * md5 盐值加密密码
     * @param user
     * @return
     */
    public String realm(ShootUser user){
        // 将用户编码作为盐值
        ByteSource salt = ByteSource.Util.bytes(user.getUserCode());
        /*
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户编码
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         * */
        String newPs = new SimpleHash("MD5", user.getUserPassword(), salt, 1024).toHex();
        return newPs;
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,
    rollbackFor = {Exception.class})
    @Override
    public boolean saveUser(ShootUser user, ShootAddress address, ShootProw prow, ShootCity city, ShootCountry country, ShootUserRole userRole) {
        QueryWrapper<ShootProw> queryProw=new QueryWrapper<>(); //sql对象
        queryProw.eq("prow",prow.getProw());  //条件
        ShootProw shootProw=shootProwMapper.selectOne(queryProw);
        QueryWrapper<ShootCity> queryCity=new QueryWrapper<>();
        queryCity.eq("city",city.getCity());
        ShootCity shootCity=shootCityMapper.selectOne(queryCity);
        QueryWrapper<ShootCountry> queryCountry=new QueryWrapper<>();
        queryCountry.eq("country",country.getCountry());
        ShootCountry shootCountry=shootCountryMapper.selectOne(queryCountry);
        Long prowId=shootProw.getId();
        Long cityId=shootCity.getId();
        Long countryId=shootCountry.getId();
        QueryWrapper<ShootAddress> queryAddress=new QueryWrapper<>();
        queryAddress.eq("prow_id",prowId);
        queryAddress.eq("city_id",cityId);
        queryAddress.eq("country_id",countryId);
        ShootAddress shootAddress=shootAddressMapper.selectOne(queryAddress);
        if(shootAddress!=null){  //说明这个信息有过人用并且一致
            user.setShootAddressId(shootAddress.getId());  //获得addressId
        }else {  //说明没有人用则需要添加一天新的数据
            address.setProwId(prowId);
            address.setCityId(cityId);
            address.setCountryId(countryId);
            shootAddressMapper.insertAddress(address);  //插入数据到address并回写获得新增id
            user.setShootAddressId(address.getId());  //加入新增id
        }
         user.setCreationDate(LocalDateTime.now());  //获得当前时间
         user.setUserCode(round.round());   //随机账号
         user.setUserPassword(realm(user));  //密码MD5加密
        int count=shootUserMapper.insertUser(user);  //执行新增用户操作  并回写获得id
        userRole.setUserId(user.getId());         //回写新增id放入
        int val=shootUserRoleMapper.insert(userRole);   //新增关联userRole
        if(count>0 && val>0){   //插入成功 新增关联成功
            return true;
        }
        return false;
    }

    @Override
    public List<ShootUser> findUserByUserCode(String userCode) {
        return shootUserMapper.loginByUserCode(userCode);
    }

}