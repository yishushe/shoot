package cn.bdqn.photography.shootletter.mapper;

import cn.bdqn.photography.shootletter.entity.ShootLetter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface ShootLetterMapper extends BaseMapper<ShootLetter> {


    /**
     * 根据当前 id 查询给我发送 约拍 私信的人 查看我的本条信息
     * @param putUserId
     * @param infoId
     * @return
     */
    List<ShootLetter> getLetterByPutUserId(@Param("putUserId") Long putUserId,
                                           @Param("infoId") Long infoId);


    /**
     * 私信 查看 别人回复 和你自己的发送
     * @param sendUserId
     * @param putUserId
     * @return
     */
    List<ShootLetter> getLetterBySendUserIdAndPutUserId(@Param("sendUserId") Long sendUserId,
                                                        @Param("putUserId") Long putUserId);


    /**
     * 我发送的 约拍信息 记录
     * @param sendUserId
     * @return
     */
    List<ShootLetter> getLetterBySendUserIdSend(@Param("sendUserId") Long sendUserId);


    /**
     * 我接受的 约拍 留言 记录
     * @param putUserId
     * @return
     */
    List<ShootLetter> getLetterByPutUserIdPut(@Param("putUserId") Long putUserId,@Param("costName")
                                              String costName);


    /**
     * 根据 发起人id 和 接受人id 查找发起人的详细信息
     * \
     * @param sendUserId
     * @param putUserId
     * @return
     */
   ShootLetter getLetterBySendUserIdAndPutUserId2(@Param("sendUserId") Long sendUserId,@Param("putUserId") Long putUserId,
                                                  @Param("infoId") Long infoId);


    /**
     * 查询我的待执行 约拍
     * @param putUserId
     * @return
     */
   List<ShootLetter> getLetterConset(@Param("putUserId") Long putUserId,
                                     @Param("stateId") Long stateId);


    /**
     * 查看已完成约拍 状态和 userId
     * @param stateId
     * @param userId
     * @return
     */
   List<ShootLetter> getOrderByStateIdAndUserId(@Param("stateId") Long stateId,
                                                @Param("userId") Long userId);

    List<ShootLetter> getLetterByPutUserIdPut(@Param("putUserId") Long putUserId);


}

