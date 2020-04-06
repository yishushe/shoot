package cn.bdqn.photography.shootinform.entity;

import cn.bdqn.photography.shootinfo.entity.ShootInfo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShootInform implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String content;

    @TableField("putUserId")
    private Long putUserId;

    @TableField("creationDate")
    private LocalDateTime creationDate;

    @TableField("infoId")
    private Long infoId;

    @TableField(exist = false)
    private ShootInfo shootInfo;

}
