package cn.bdqn.photography.shootletter.service;

import cn.bdqn.photography.shootletter.entity.ShootLetter;
<<<<<<< HEAD
import com.baomidou.mybatisplus.core.metadata.IPage;
=======
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
>>>>>>> 59aa669a0946cfe4b46498c5bebc50bc3d72d1f7
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface IShootLetterService extends IService<ShootLetter> {

    /**
     * 根据 当前用户 id 查询出给我发送约拍请求 私信的信息
     * @param putUserId
     * @return
     */
    List<ShootLetter> findLetterByPutUserId(Long putUserId);


    /**
     * 查看你来我往信息
     * @param sendUserId
     * @return
     */
    List<ShootLetter> findLetterBySnedUserIdAndPutUserId(Long sendUserId,Long putUserId);


    /**
     * 我发送的 约拍信息 记录
     * @param sendUserId
     * @return
     */
    List<ShootLetter> findLetterBySendUserIdSend(Long sendUserId);


    /**
     * 我接受的 约拍 留言 记录
     * @param putUserId
     * @return
     */
    List<ShootLetter> findLetterByPutUserIdPut(Long putUserId);

    /**
<<<<<<< HEAD
     *分页
     * @param page
     * @return
     */
    IPage<ShootLetter> selectPage(IPage<ShootLetter> page,int current);
=======
     * 查询所有用户
     *
     *
     */
    IPage<ShootLetter> selectPage(IPage<ShootLetter> page/*, @Param(Constants.WRAPPER) Wrapper<ShootLetter> queryWrapper*/);
    /*Page<ShootLetter> findLetterPage(Long id,int current);*/
>>>>>>> 59aa669a0946cfe4b46498c5bebc50bc3d72d1f7

}
