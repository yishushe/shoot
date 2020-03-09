package cn.bdqn.photography.shootletter.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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

    @TableField("giveContent")
    private String giveContent;

    @TableField("replyContent")
    private String replyContent;

    @TableField("giveUserID")
    private String giveUserID;

    @TableField("repltUserId")
    private String repltUserId;

    @TableField("creationDate")
    private LocalDateTime creationDate;


}
