package cn.bdqn.photography.shootletter.service.impl;

import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.mapper.ShootLetterMapper;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Service
public class ShootLetterServiceImpl extends ServiceImpl<ShootLetterMapper, ShootLetter> implements IShootLetterService {

    @Autowired
    private ShootLetterMapper shootLetterMapper;

    @Override
    public List<ShootLetter> findLetterByPutUserId(Long putUserId,Long infoId) {
        return shootLetterMapper.getLetterByPutUserId(putUserId,infoId);
    }

    @Override
    public List<ShootLetter> findLetterBySnedUserIdAndPutUserId(Long sendUserId, Long putUserId) {
        return shootLetterMapper.getLetterBySendUserIdAndPutUserId(sendUserId,putUserId);
    }

    @Override
    public List<ShootLetter> findLetterBySendUserIdSend(Long sendUserId) {
        return shootLetterMapper.getLetterBySendUserIdSend(sendUserId);
    }

    @Override
    public List<ShootLetter> findLetterByPutUserIdPut(Long putUserId,String costName) {
        return shootLetterMapper.getLetterByPutUserIdPut(putUserId,costName);
    }

    @Override
    public ShootLetter findLetterBySendUserIdAndPutUserId2(Long sendUserId, Long putUserId,Long infoId) {
        return shootLetterMapper.getLetterBySendUserIdAndPutUserId2(sendUserId,putUserId,infoId);
    }

    @Override
    public List<ShootLetter> findLetterConste(Long putUserId, Long stateId) {
        return shootLetterMapper.getLetterConset(putUserId,stateId);
    }

    @Override
    public List<ShootLetter> findOrderByStateIdAndUserId(Long stateId, Long userId) {
        return shootLetterMapper.getOrderByStateIdAndUserId(stateId,userId);
    }

    @Override
    public Integer findOrder(Long sendUserId, Long putUserId, Long infoId,String test) {
        return shootLetterMapper.getByOrder(sendUserId,putUserId,infoId,test);
    }
    @Override

    public IPage<ShootLetter> selectPage(IPage<ShootLetter> page,int current) {
        return shootLetterMapper.getLetterById(page,current);
    }


    public Page<ShootLetter> selectPage(IPage<ShootLetter> page/*, Wrapper<ShootLetter> queryWrapper*/) {
        return shootLetterMapper.getLetterById(page);
    }

}
