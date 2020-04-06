package cn.bdqn.photography.shootinform.service;

import cn.bdqn.photography.shootinform.entity.ShootInform;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IShootInformService extends IService<ShootInform> {


    /**
     * 当前用户的 通知
     * @param putUserId
     * @return
     */
    List<ShootInform> findByPutUserId(Long putUserId);

}
