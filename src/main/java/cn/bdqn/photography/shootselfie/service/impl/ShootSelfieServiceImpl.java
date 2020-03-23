package cn.bdqn.photography.shootselfie.service.impl;

import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
import cn.bdqn.photography.shootselfie.mapper.ShootSelfieMapper;
import cn.bdqn.photography.shootselfie.service.IShootSelfieService;
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
public class ShootSelfieServiceImpl extends ServiceImpl<ShootSelfieMapper, ShootSelfie> implements IShootSelfieService {

    @Autowired
    private ShootSelfieMapper shootSelfieMapper;

    @Override
    public List<ShootSelfie> findSelfieLIst() {
        return shootSelfieMapper.getSelfieList();
    }

}
