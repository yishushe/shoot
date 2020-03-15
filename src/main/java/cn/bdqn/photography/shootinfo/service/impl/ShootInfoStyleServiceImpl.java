package cn.bdqn.photography.shootinfo.service.impl;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.entity.ShootInfoStyle;
import cn.bdqn.photography.shootinfo.mapper.ShootInfoMapper;
import cn.bdqn.photography.shootinfo.mapper.ShootInfoStyleMapper;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootinfo.service.IShootInfoStyleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShootInfoStyleServiceImpl extends ServiceImpl<ShootInfoStyleMapper, ShootInfoStyle> implements IShootInfoStyleService {

}
