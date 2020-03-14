package cn.bdqn.photography.shootinfo.mapper;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface ShootInfoMapper extends BaseMapper<ShootInfo> {

    int addInfo(ShootInfo info);

}
