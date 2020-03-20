package cn.bdqn.photography.shootattention.mapper;

import cn.bdqn.photography.shootattention.entity.ShootAttention;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jdk.internal.dynalink.linker.LinkerServices;
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
public interface ShootAttentionMapper extends BaseMapper<ShootAttention> {

    /**
     * 根据 当前 用户id 查找已关注的用户
     * @param attentionId
     * @return
     */
    List<ShootAttention> getByAttentionId(@Param("attentionId") Long attentionId,@Param("message")
            String message);


    /**
     * 查询 我关注的用户 的 约拍信息
     * @param attentionId
     * @return
     */
    List<ShootInfo> getByAttentionIdInfo(@Param("attentionId") Long attentionId);
}
