package cn.bdqn.photography.shootorder.service.impl;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootorder.entity.ShootOrder;
import cn.bdqn.photography.shootorder.mapper.ShootOrderMapper;
import cn.bdqn.photography.shootorder.service.IShootOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShootOrderServiceImpl extends ServiceImpl<ShootOrderMapper, ShootOrder> implements IShootOrderService {
    @Autowired
    private ShootOrderMapper shootOrderMapper;

    @Override
    public Page<ShootOrder> showallorder(int current,String id) {
        IPage<ShootOrder> page=new Page<>(current,5);
        Page<ShootOrder> infoByThemeId = shootOrderMapper.showallorder(page,id);
        return infoByThemeId;
    }
}
