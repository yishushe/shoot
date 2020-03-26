package cn.bdqn.photography.shootorder.service;

import cn.bdqn.photography.shootorder.entity.ShootOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShootOrderService extends IService<ShootOrder> {
    Page<ShootOrder> showallorder(int current,String id);

}
