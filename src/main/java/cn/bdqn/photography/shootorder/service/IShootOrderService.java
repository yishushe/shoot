package cn.bdqn.photography.shootorder.service;

import cn.bdqn.photography.shootorder.entity.ShootOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShootOrderService extends IService<ShootOrder> {
    List<ShootOrder> showallorder(String id);
    List<ShootOrder> showorderby();

    /**
     * 总支出
     * @param userId
     * @return
     */
    Float expend(Long userId);

    /**
     * 总收入
     * @param sendUserId
     * @return
     */
    Float income(Long sendUserId);

}
