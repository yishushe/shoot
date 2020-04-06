package cn.bdqn.photography.shootinform.service;

import cn.bdqn.photography.shootinform.entity.ShootInform;
import cn.bdqn.photography.shootinform.mapper.ShootInformMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShootInformServiceImpl extends ServiceImpl<ShootInformMapper, ShootInform> implements IShootInformService {

    @Autowired
    private ShootInformMapper shootInformMapper;

    @Override
    public List<ShootInform> findByPutUserId(Long putUserId) {
        return shootInformMapper.getListByPutUserId(putUserId);
    }
}
