package cn.bdqn.photography.shootOrder.entity;

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
public class ShootOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("outTradeNo")
    private String outTradeNo;

    @TableField("totalAmount")
    private Float totalAmount;

    @TableField("subject")
    private String subject;

    @TableField("body")
    private String body;

    @TableField("userId")
    private Long userId;

    @TableField("creationDate")
    private LocalDateTime creationDate;

}
