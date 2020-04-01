package cn.bdqn.photography.shootuser.mapper;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.shootuser.entity.ShootUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
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


    ShootUser getByUserId(@Param("id") Long id);   //查询个人信息


    /**
     * 添加会员
     * @param id
     * @param memberDate
     * @return
     */
    int updateMember(@Param("id") Long id,
                     @Param("memberDate")LocalDate memberDate);

    Page<ShootUser> getpermission(IPage<ShootUser> page,@Param("id")Long id);  //查询权限

    Long seleid(@Param("rid")Long id,@Param("pid")Long pid);  //查询是否有此权限

    boolean updp(@Param("rid")Long id,@Param("pid")Long pid,@Param("qxid")Long qxid); //更改权限

    boolean ins(@Param("rid")Long id,@Param("pid")Long pid);//增加权限

    boolean del(@Param("pid")Long id,@Param("rid")Long rid);//根据权限名称删除权限

    /**
     * 根据id 提交 保证金
     * @param id
     * @param securityMoney
     * @return
     */
    int updateSecurityMoney(@Param("id") Long id,@Param("securityMoney") Float securityMoney);
}

