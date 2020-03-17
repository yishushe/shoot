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
     * 根据当前 id 查询给我发送 约拍 私信的人
     * @param putUserID
     * @return
     */
    List<ShootLetter> getLetterByPutUserId(@Param("putUserId") Long putUserID);


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
    List<ShootLetter> getLetterByPutUserIdPut(@Param("putUserId") Long putUserId);
}
