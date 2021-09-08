package com.ioariroi.host.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ioariroi
 * @since 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_host_type")
@ApiModel(value="HostType对象", description="")
public class HostType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "type_id")
    private Long typeId;

    @ApiModelProperty(value = "類別名")
    private String name;

    @ApiModelProperty(value = "更新人員")
    private Long updateUser;

    @ApiModelProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_time")
    private Date createTime;


}
