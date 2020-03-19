package cn.bdqn.photography.shootattention.entity;

import java.time.LocalDateTime;

import cn.bdqn.photography.shootimages.entity.ShootImages;
import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import cn.bdqn.photography.shootinfo.entity.ShootType;
import cn.bdqn.photography.shootuser.entity.ShootAddress;
import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
public class ShootAttention implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("attentionId")
    private Long attentionId;

    @TableField("focusedId")
    private Long focusedId;

    @TableField("creationDate")
    private LocalDateTime creationDate;

    @TableField(exist = false)
    private ShootUser shootUser;

    @TableField(exist = false)
    private ShootAddress shootAddress;

    @TableField(exist = false)
    private ShootType shootType;

    @TableField(exist = false)
    private List<ShootInfo> shootInfos;

}
