package com.ioariroi.host.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "HostInfo查询对象", description = "主機查询对象封装")
@Data
public class HostInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "類別")
    private Long typeId;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "主機名称,模糊查询")
    private String hostname;

    @ApiModelProperty(value = "主機地址,模糊查询")
    private String ipAddress;
}
