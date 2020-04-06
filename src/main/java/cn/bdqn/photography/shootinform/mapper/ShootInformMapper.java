package cn.bdqn.photography.shootinform.mapper;

import cn.bdqn.photography.shootinform.entity.ShootInform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShootInformMapper extends BaseMapper<ShootInform> {

    /**
     * 查询当前用户的 通知
     * @param putUserId
     * @return
     */
    List<ShootInform> getListByPutUserId(@Param("putUserId") Long putUserId);

}
