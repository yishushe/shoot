package cn.bdqn.photography.shootletter.service.impl;

import cn.bdqn.photography.shootletter.entity.ShootLetter;
import cn.bdqn.photography.shootletter.mapper.ShootLetterMapper;
import cn.bdqn.photography.shootletter.service.IShootLetterService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
    public List<ShootLetter> findLetterByPutUserId(Long putUserId) {
        return shootLetterMapper.getLetterByPutUserId(putUserId);
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
    public List<ShootLetter> findLetterByPutUserIdPut(Long putUserId) {
        return shootLetterMapper.getLetterByPutUserIdPut(putUserId);
    }

    @Override
    public Page<ShootLetter> selectPage(IPage<ShootLetter> page/*, Wrapper<ShootLetter> queryWrapper*/) {
        return shootLetterMapper.getLetterById(page);
    }

   /* @Override
    public Page<ShootLetter> findLetterPage(Long id, int current) {
        IPage<ShootLetter> page = new Page<>(current,5);
        Page<ShootLetter> infoById = shootLetterMapper.getLetterById(page,id);
        return infoById;
    }*/

}
