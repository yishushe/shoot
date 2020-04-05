package cn.bdqn.photography.shootselfie.mapper;

import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface ShootSelfieMapper extends BaseMapper<ShootSelfie> {

    List<ShootSelfie> getSelfieList();  //查询所有自拍信息

    ShootSelfie getById(@Param("id") Long id); //查看自拍详情

    /**
     * 查询自己的自拍
     * @param userId
     * @return
     */
    List<ShootSelfie> getSelfieByUserId(@Param("userId") Long userId);

}
