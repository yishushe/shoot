package cn.bdqn.photography.shootorder.mapper;

import cn.bdqn.photography.shootorder.entity.ShootOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShootOrderMapper extends BaseMapper<ShootOrder> {
List<ShootOrder> showallorder(@Param("uid") String id);
List<ShootOrder> showorderby();
}
