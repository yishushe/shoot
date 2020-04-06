package cn.bdqn.photography.shootevaluate.service;

import cn.bdqn.photography.shootevaluate.entity.ShootEvaluate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IShootEvaluateService extends IService<ShootEvaluate> {


    /**
     * 查询对自己的评价1
      * @param putUserId
     * @return
     */
    List<ShootEvaluate> findListByPutUserId(Long putUserId);

}

