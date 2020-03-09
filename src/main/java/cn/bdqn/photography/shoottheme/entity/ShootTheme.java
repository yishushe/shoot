package cn.bdqn.photography.shoottheme.entity;

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
public class ShootTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("themName")
    private String themName;

    @TableField("infoId")
    private String infoId;


}
