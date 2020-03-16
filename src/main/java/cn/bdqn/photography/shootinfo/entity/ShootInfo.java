package cn.bdqn.photography.shootinfo.entity;

import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShootInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String content;

    private Float price;

    @TableField("takeTime")
    private String takeTime;

    @TableField("takeLocation")
    private String takeLocation;

    @TableField("takeResult")
    private String takeResult;

    @TableField("creationDate")
    private LocalDate creationDate;

    @TableField("typeId")
    private String typeId;

    @TableField("costId")
    private Long costId;

    //是否有会员
    @TableField("requireRoles")
    private Long requireRoles;

    //是否需要存入保证金
    @TableField("requireSecurityAccount")
    private Long requireSecurityAccount;

    //状态
    @TableField("stateId")
    private Long stateId;

    //用户id
    @TableField("userId")
    private Long userId;

    //关联地址字段
    @TableField("shootAddressId")
    private Long shootAddressId;

    //主题字段
    @TableField("themeId")
    private Long themeId;

    //地址表
    @TableField(exist = false)
    private ShootAddress shootAddress;

    //infostle表
    @TableField(exist = false)
    private ShootInfoStyle shootInfoStyle;

    //类型表
    @TableField(exist = false)
    private ShootType shootType;

    //费用表
    @TableField(exist = false)
    private ShootCost shootCost;

    //状态表
    @TableField(exist = false)
    private ShootState shootState;

    //用户表
    @TableField(exist = false)
    private ShootUser shootUser;

    //图片表
    @TableField(exist = false)
    private List<ShootImages> shootImages;

}
