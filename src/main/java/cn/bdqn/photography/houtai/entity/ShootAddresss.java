package cn.bdqn.photography.houtai.entity;

import cn.bdqn.photography.common.entity.ShootCity;
import cn.bdqn.photography.common.entity.ShootCountry;
import cn.bdqn.photography.common.entity.ShootProw;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShootAddresss implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)  //设置主键生成策略是id自增
    private Long id;

    private Long prowId;

    private Long cityId;

    private Long countryId;

    @TableField(exist = false)
    private ShootProw shootProw;

    @TableField(exist = false)
    private ShootCity shootCity;

    @TableField(exist = false)
    private ShootCountry shootCountry;

}
