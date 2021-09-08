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
@TableName("tbl_host_account")
@ApiModel(value="HostAccount对象", description="")
public class HostAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "域名")
    private String domain;

    @ApiModelProperty(value = "使用者")
    private String username;

    @ApiModelProperty(value = "密碼")
    private String password;

    @ApiModelProperty(value = "更新人員")
    private Long updateUser;

    @ApiModelProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_time")
    private Date createTime;


}
