package cn.bdqn.photography.shootinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDate;

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

    //infostle表
    @TableField(exist = false)
    private ShootInfoStyle shootInfoStyle;

}
