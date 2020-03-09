package cn.bdqn.photography.shootattention.entity;

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
public class ShootAttention implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("attentionId")
    private String attentionId;

    @TableField("focusedId")
    private String focusedId;

    @TableField("creationDate")
    private LocalDateTime creationDate;


}
