package cn.bdqn.photography.shootinfo.service;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
public interface IShootInfoService extends IService<ShootInfo> {

       boolean addInfo(ShootInfo shootInfo, ShootImages shootImages,
                    HttpSession session, MultipartFile[] multipartFiles,
                    HttpServletRequest request, ShootProw prow,
                    ShootCity city, ShootCountry country,String styleName);  //添加约拍信息

       IPage<ShootInfo> findInfo(Long stateId, String city, Long costId,
                                 Long roleIds,Long sex, int current);  //查询通过审核约拍信息

       ShootInfo findInfoById(Long id);   //约拍详情页

       ShootInfo findInfoMessageById(Long id);  //发起约拍信息详情页数据

       List<ShootInfo> findInfoByUserId(Long userId);  //查看当前用户约拍信息

       Page<ShootInfo> getInfoByStateId(Long id, int current);//根据状态查询数据
       List<ShootInfo> getinfobyinfoid(Long id);   //根据infoid查询

       int modifyStateIdById(Long id,Long stateId);   //根据id更改信息状态

}

