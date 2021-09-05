package com.ioariroi.host.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
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
 * @since 2021-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_host_info")
@ApiModel(value="HostInfo对象", description="")
public class HostInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("TYPE_ID")
    private Long typeId;

    @TableField("HOSTNAME")
    private String hostname;

    @TableField("IP_ADDRESS")
    private String ipAddress;

    @TableField("PORT")
    private Long port;

    @TableField("REMARK")
    private String remark;

    @TableField("UPDATE_USER")
    private Long updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
