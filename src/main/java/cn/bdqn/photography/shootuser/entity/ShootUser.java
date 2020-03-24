package cn.bdqn.photography.shootuser.entity;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShootUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)  //设置主键生成策略是id自增
    private Long id;

    @TableField("userCode")
    private String userCode;

    @TableField("userName")
    private String userName;

    @TableField("userPassWord")
    private String userPassword;

    private Long sex;

    @TableField("creationDate")
    private LocalDateTime creationDate;

    @TableField("portyaitl")
    private String portyaitl;

    @TableField("phone")
    private String phone;

    @TableField("shootAddressId")
    private Long shootAddressId;

    @TableField("member")
    private Long member;

    @TableField("memberDate")
    private LocalDate memberDate;

    @TableField("securityMoney")
    private Float securityMoney;

    @TableField("integral")
    private Long integral;

    @TableField(exist = false)
    private ShootAddress shootAddress;

    @TableField(exist = false)  //忽略字段
    private List<ShootRole> roles;

    @TableField(exist = false)
    private List<ShootInfo> shootInfos;

}