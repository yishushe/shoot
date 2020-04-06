package cn.bdqn.photography.shootuser.mapper;

import cn.bdqn.photography.shootuser.entity.ShootAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
public interface ShootAddressMapper extends BaseMapper<ShootAddress> {

    int insertAddress(ShootAddress address);  //插入地址数据并回写新插入数据id

}