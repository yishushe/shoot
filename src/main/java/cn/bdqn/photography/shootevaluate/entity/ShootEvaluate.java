package cn.bdqn.photography.shootevaluate.entity;

import cn.bdqn.photography.shootuser.entity.ShootUser;
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
public class ShootEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String evaluate;

    @TableField("sendUserId")
    private Long sendUserId;

    @TableField("putUserId")
    private Long putUserId;

    @TableField("creationDate")
    private LocalDateTime creationDate;

    @TableField(exist = false)
    private ShootUser shootUser;

}
