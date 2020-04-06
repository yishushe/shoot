package cn.bdqn.photography.shootimages.mapper;

import cn.bdqn.photography.shootimages.entity.ShootImages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface ShootImagesMapper extends BaseMapper<ShootImages> {
List<ShootImages> imglist();
}
