package cn.bdqn.photography.shootattention.service;

import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface IShootAttentionService extends IService<ShootAttention> {

    /**
     * 查询 当前用户 关注的人
     * @param attentionId
     * @return
     */
    List<ShootAttention> findByAttentionId(Long attentionId,String message);

    /**
     * 查询 我关注的用户 的 约拍信息
     * @param attentionId
     * @return
     */
    List<ShootInfo> findByAttentionIdInfo(Long attentionId);
}
