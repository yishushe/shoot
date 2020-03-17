package cn.bdqn.photography.shoottheme.service.impl;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shoottheme.entity.ShootTheme;
import cn.bdqn.photography.shoottheme.mapper.ShootThemeMapper;
import cn.bdqn.photography.shoottheme.service.IShootThemeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Service
public class ShootThemeServiceImpl extends ServiceImpl<ShootThemeMapper, ShootTheme> implements IShootThemeService {
        @Autowired
        private ShootThemeMapper shootThemeMapper;
    @Override
    public List<ShootInfo> selebythemeid(ShootTheme shootTheme) {
        return shootThemeMapper.selebythemeid(shootTheme);
    }

    @Override
    public Page<ShootInfo> findInfoByThemeId(Long id,String city,int current) {
        IPage<ShootInfo> page=new Page<>(current,5);
        Page<ShootInfo> infoByThemeId = shootThemeMapper.getInfoByThemeId(page, id,city);
        return infoByThemeId;
    }
}
