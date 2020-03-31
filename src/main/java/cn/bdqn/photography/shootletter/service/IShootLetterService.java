package cn.bdqn.photography.shootletter.service;

import cn.bdqn.photography.shootletter.entity.ShootLetter;
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
     * 根据 当前用户 id 查询出给我发送约拍请求 私信的信息 查看我的本条信息
     * @param putUserId
     * @return
     */
    List<ShootLetter> findLetterByPutUserId(Long putUserId,Long infoId);


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
    List<ShootLetter> findLetterByPutUserIdPut(Long putUserId,String costName);


    /**
     * 根据 发起人id 和 接受人id 查找发起人的详细信息
     * \
     * @param sendUserId
     * @param putUserId
     * @return
     */
    ShootLetter findLetterBySendUserIdAndPutUserId2(@Param("sendUserId") Long sendUserId, @Param("putUserId") Long putUserId,
                                                    Long infoId);


    /**
     * 查询我的待约拍 信息
     * @param putUserId
     * @param stateId
     * @return
     */
    List<ShootLetter> findLetterConste(Long putUserId,Long stateId);


    /**
     * 查看已完成约拍 stteId userId
     * @param stateId
     * @param userId
     * @return
     */
    List<ShootLetter> findOrderByStateIdAndUserId(Long stateId,Long userId);
}

