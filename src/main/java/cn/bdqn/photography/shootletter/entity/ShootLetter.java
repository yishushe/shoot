package cn.bdqn.photography.shootletter.entity;

import java.time.LocalDateTime;

import cn.bdqn.photography.shootuser.entity.ShootUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
public class ShootLetter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String content;

    @TableField("sendUserId")
    private Long sendUserId;

    @TableField("putUserId")
    private Long putUserId;

    @TableField("creationDate")
    private LocalDateTime creationDate;

    @TableField("infoId")
    private Long infoId;

    @TableField(exist = false)
    private ShootUser shootUsers;

    @TableField(exist = false)
    private ShootUser shootUser;

}
