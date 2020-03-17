package cn.bdqn.photography.shoottheme.service;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface IShootThemeService extends IService<ShootTheme> {

    List<ShootInfo> selebythemeid(ShootTheme shootTheme);

    Page<ShootInfo> findInfoByThemeId(Long id,String city,int current);

}
