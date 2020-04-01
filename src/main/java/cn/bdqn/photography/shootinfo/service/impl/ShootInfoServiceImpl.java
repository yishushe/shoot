package cn.bdqn.photography.shootinfo.service.impl;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootimages.mapper.ShootImagesMapper;
import cn.bdqn.photography.shootimages.service.IShootImagesService;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.entity.ShootInfoStyle;
import cn.bdqn.photography.shootinfo.mapper.ShootInfoMapper;
import cn.bdqn.photography.shootinfo.service.IShootInfoService;
import cn.bdqn.photography.shootinfo.service.IShootInfoStyleService;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import cn.bdqn.photography.utils.AddressUtls;
import cn.bdqn.photography.utils.IsPath;
import cn.bdqn.photography.utils.SplitName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
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
@Transactional  //事物
public class ShootInfoServiceImpl extends ServiceImpl<ShootInfoMapper, ShootInfo> implements IShootInfoService {

    @Autowired
    private IsPath isPath;

    @Autowired
    private ShootInfoMapper shootInfoMapper;

    @Autowired
    private IShootImagesService iShootImagesService;

    @Autowired
    private AddressUtls addressUtls;

    @Autowired
    private IShootInfoStyleService iShootInfoStyleService;

    @Autowired
    private SplitName splitName;

    @Autowired
    private IShootInfoService iShootInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,
            rollbackFor = {Exception.class})  //事物出现错误则回滚
    public boolean addInfo(ShootInfo shootInfo, ShootImages shootImages,
                           HttpSession session, MultipartFile[] multipartFiles,
                           HttpServletRequest request, ShootProw prow,
                           ShootCity city, ShootCountry country,String styleName) {

        //获得对应address地址id
        shootInfo.setShootAddressId(addressUtls.address(prow,city,country));
        shootInfo.setCreationDate(LocalDate.now());   //添加当前时间

        Subject subject= SecurityUtils.getSubject();
        ShootUser user = (ShootUser)subject.getSession().getAttribute("user");

        shootInfo.setUserId(user.getId());   //存入当前用户id

        int insert = shootInfoMapper.addInfo(shootInfo);  //增加信息并回调id

        //添加标签style
        List<ShootInfoStyle> list1=new ArrayList<>();
        Long[] strings = splitName.styleName(styleName);
        for (int i=0;i<strings.length;i++){
            ShootInfoStyle style=new ShootInfoStyle();
            style.setInfoId(shootInfo.getId());
            style.setStyleId(strings[i]);
            list1.add(style);
        }
        iShootInfoStyleService.saveBatch(list1);  //添加风格标签

        String[] upload = isPath.upload(multipartFiles, request, session);
        List<ShootImages> list=new ArrayList<>();
        for (int i=0;i<upload.length;i++){  //多张图片添加
            ShootImages images=new ShootImages();
            images.setInfoId(shootInfo.getId());
            images.setImagesName(upload[i]);
            list.add(images);
        }
        boolean b = iShootImagesService.saveBatch(list);  //添加多张图片
        return b;
    }

    @Override
    public IPage<ShootInfo> findInfo(Long stateId,String city,Long costId,
                                    Long roleIds,Long sex,int current) {
        //分页帮助类
        IPage<ShootInfo> page=new Page<>(current,5);
        page= shootInfoMapper.getInfo(page,stateId, city, costId, roleIds,sex);
        System.out.println("page:"+page.getRecords().size());
        return page;
    }


    @Override
    public ShootInfo findInfoById(Long id) {
        return shootInfoMapper.getInfoById(id);
    }

    @Override
    public ShootInfo findInfoMessageById(Long id) {
        return shootInfoMapper.getInfoMessageById(id);
    }

    @Override
    public List<ShootInfo> findInfoByUserId(Long userId) {
        return shootInfoMapper.getInfoByUserId(userId);
    }

    @Override
    public Page<ShootInfo> getInfoByStateId(int current,Long stateid) {
        IPage<ShootInfo> p=new Page<ShootInfo>(current,5);
        Page<ShootInfo> ps=shootInfoMapper.getinfobystateid(p,stateid);
        return ps;
    }


    @Override
    public List<ShootInfo> getinfobyinfoid(Long id) {
        return shootInfoMapper.getinfobyinfoid(id);
    }

    @Override
    public int modifyStateIdById(Long id,Long stateId) {
        return shootInfoMapper.updateStateIdById(id,stateId);
    }

    @Override
    public boolean insertinform(String cause, Long pid, LocalDate date,Long infoId) {
        return shootInfoMapper.insertinform(cause,pid,date,infoId);
    }


}
