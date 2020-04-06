package cn.bdqn.photography.shootevaluate.mapper;

import cn.bdqn.photography.shootevaluate.entity.ShootEvaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShootEvalateMapper extends BaseMapper<ShootEvaluate> {


    /**
     * 查看我的评价
     * @param putUserId
     * @return
     */
    List<ShootEvaluate> orderListByPutUserId(@Param("putUserId") Long putUserId);

}
