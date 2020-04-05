package cn.bdqn.photography.shootselfie.service;

import cn.bdqn.photography.shootselfie.entity.ShootSelfie;
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
public interface IShootSelfieService extends IService<ShootSelfie> {

    List<ShootSelfie> findSelfieLIst();   //查询所有自拍信息


    ShootSelfie findById(Long id);   //查看自拍详情


    /**
     * 查询自己的 自拍
     * @param userId
     * @return
     */
    List<ShootSelfie> findSelfieByUserId(Long userId);

}
