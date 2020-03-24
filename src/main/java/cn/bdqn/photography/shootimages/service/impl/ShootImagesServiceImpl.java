package cn.bdqn.photography.shootimages.service.impl;

import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.mapper.ShootImagesMapper;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
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
public class ShootImagesServiceImpl extends ServiceImpl<ShootImagesMapper, ShootImages> implements IShootImagesService {
    //查询所有图片
@Autowired
private ShootImagesMapper shootImages;
    @Override
    public List<ShootImages> imglist() {
        return shootImages.imglist();
    }
}
