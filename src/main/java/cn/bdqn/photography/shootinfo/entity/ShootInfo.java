package cn.bdqn.photography.shootinfo.entity;

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
public class ShootInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String content;

    private String price;

    @TableField("timeThat")
    private String timeThat;

    private String instractions;

    @TableField("descriPtion")
    private String descriPtion;

    @TableField("creationDate")
    private String creationDate;

    @TableField("typeId")
    private String typeId;

    @TableField("styleId")
    private String styleId;

}
