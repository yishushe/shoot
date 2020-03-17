package cn.bdqn.photography.shoottheme.mapper;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface ShootThemeMapper extends BaseMapper<ShootTheme> {

    List<ShootInfo> selebythemeid(ShootTheme shootTheme);

    Page<ShootInfo> getInfoByThemeId(IPage<ShootInfo> page, @Param("themeId") Long themeId,
                                     @Param("city") String city);  //查询主题约拍信息
}
