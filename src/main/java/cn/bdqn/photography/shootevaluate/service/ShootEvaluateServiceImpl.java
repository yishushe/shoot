package cn.bdqn.photography.shootevaluate.service;

import cn.bdqn.photography.shootevaluate.entity.ShootEvaluate;
import cn.bdqn.photography.shootevaluate.mapper.ShootEvalateMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShootEvaluateServiceImpl extends ServiceImpl<ShootEvalateMapper, ShootEvaluate> implements IShootEvaluateService {

    @Autowired
    private ShootEvalateMapper shootEvalateMapper;

    @Override
    public List<ShootEvaluate> findListByPutUserId(Long putUserId) {
        return shootEvalateMapper.orderListByPutUserId(putUserId);
    }
}
