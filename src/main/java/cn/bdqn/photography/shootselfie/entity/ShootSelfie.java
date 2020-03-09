package cn.bdqn.photography.shootselfie.entity;

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
public class ShootSelfie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("imagesName")
    private String imagesName;

    private String content;

    @TableField("creationDate")
    private LocalDateTime creationDate;


}
