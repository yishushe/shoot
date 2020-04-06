package cn.bdqn.photography.shootuser.service.impl;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.common.mapper.ShootCityMapper;
import cn.bdqn.photography.common.mapper.ShootCountryMapper;
import cn.bdqn.photography.common.mapper.ShootProwMapper;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import cn.bdqn.photography.shootuser.mapper.ShootAddressMapper;
import cn.bdqn.photography.shootuser.mapper.ShootUserMapper;
import cn.bdqn.photography.shootuser.mapper.ShootUserRoleMapper;
import cn.bdqn.photography.shootuser.service.IShootUserService;
import cn.bdqn.photography.utils.AddressUtls;
import cn.bdqn.photography.utils.Round;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
@Service
@Transactional
public class ShootUserServiceImpl extends ServiceImpl<ShootUserMapper, ShootUser> implements IShootUserService {

    @Autowired
    private ShootUserMapper shootUserMapper;

    @Autowired
    private ShootProwMapper shootProwMapper;

    @Autowired
    private ShootCityMapper shootCityMapper;

    @Autowired
    private ShootCountryMapper shootCountryMapper;

    @Autowired
    //@Qualifier("shootAddressMapper")
    private ShootAddressMapper shootAddressMapper;

    @Autowired
    @Qualifier("round")
    private Round round;

    @Autowired
    private ShootUserRoleMapper shootUserRoleMapper;

    @Autowired
    private AddressUtls addressUtls;

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
    public boolean saveUser(ShootUser user, ShootProw prow, ShootCity city, ShootCountry country, ShootUserRole userRole) {
         user.setShootAddressId(addressUtls.address(prow,city,country)); //调用帮助方法 获得添加地址对应id
         user.setCreationDate(LocalDateTime.now());  //获得当前时间
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

    @Override
    public ShootUser personageByUserCode(String userCode) {
        return shootUserMapper.personageByUserCode(userCode);
    }

    @Override
    public ShootUser findByUserId(Long id) {
        return shootUserMapper.getByUserId(id);
    }

    @Override
    public List<ShootUser> getUsersAll() {
        return shootUserMapper.getUserAll();
    }

    public int modifyMember(Long id, LocalDate memberDate) {
        return shootUserMapper.updateMember(id, memberDate);
    }

    public Page<ShootUser> getpermission(int current,Long id) {
        IPage<ShootUser> iPage=new Page<>(current,5);
        Page<ShootUser> s= shootUserMapper.getpermission(iPage,id);
        return s;
    }

    @Override
    public Long seleid(Long id, Long pid) {
        return shootUserMapper.seleid(id,pid);
    }

    @Override
    public boolean updp(Long id, Long pid,Long qxid) {
        return shootUserMapper.updp(id,pid,qxid);
    }

    @Override
    public boolean ins(Long id, Long pid) {
        return shootUserMapper.ins(id,pid);
    }

    @Override
    public boolean del(Long id,Long rid) {
        return shootUserMapper.del(id,rid);
    }

    @Override
    public int modifySecurityMoney(Long id, Float securityMoney) {
        return shootUserMapper.modifySecurityMoney(id,securityMoney);
    }

    @Override
    public boolean updr(Long uid, Long rid) {
        return shootUserMapper.updr(uid,rid);
    }

    @Override
    public List<ShootUser> findUserId(Long userId) {
        return shootUserMapper.getUserId(userId);
    }

    @Override
    public Long sesurid(Long id) {
        return shootUserMapper.sesurid(id);
    }


}
