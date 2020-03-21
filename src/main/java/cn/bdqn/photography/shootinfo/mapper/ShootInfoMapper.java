package cn.bdqn.photography.shootinfo.mapper;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface ShootInfoMapper extends BaseMapper<ShootInfo> {

    int addInfo(ShootInfo info);  //添加约拍信息

    //page 分页类必须放到参数第一个
    IPage<ShootInfo> getInfo(IPage<ShootInfo> page,@Param("stateId") Long stateId,
                            @Param("city") String city,
                            @Param("costId") Long costId,
                            @Param("roleId") Long roleId,
                             @Param("sex") Long sex);  //查询所有通过审核约拍信息

    ShootInfo getInfoById(@Param("id") Long id);   //约拍详情页面


    ShootInfo getInfoMessageById(@Param("id") Long id);  //发起约拍信息详情页数据

    List<ShootInfo> getInfoByUserId(@Param("userId") Long userId);   //根据用户id查询当前用户约拍信息

    Page<ShootInfo> getinfobystateid(IPage<ShootInfo> page,@Param("id") Long id);//根据状态查信息

    List<ShootInfo> getinfobyinfoid(@Param("id") Long id);   //根据infoid查询
}
