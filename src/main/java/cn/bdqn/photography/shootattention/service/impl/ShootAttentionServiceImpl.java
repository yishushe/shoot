package cn.bdqn.photography.shootattention.service.impl;

import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootattention.mapper.ShootAttentionMapper;
import cn.bdqn.photography.shootattention.service.IShootAttentionService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
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
public class ShootAttentionServiceImpl extends ServiceImpl<ShootAttentionMapper, ShootAttention> implements IShootAttentionService {

    @Autowired
    private ShootAttentionMapper shootAttentionMapper;

    @Override
    public List<ShootAttention> findByAttentionId(Long attentionId,String message) {
        return shootAttentionMapper.getByAttentionId(attentionId,message);
    }

    @Override
    public List<ShootInfo> findByAttentionIdInfo(Long attentionId) {
        return shootAttentionMapper.getByAttentionIdInfo(attentionId);
    }

}
