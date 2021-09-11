package com.ioariroi.host.controller;


import com.ioariroi.commonutils.R;
import com.ioariroi.host.entity.HostInfo;
import com.ioariroi.host.entity.HostType;
import com.ioariroi.host.service.HostInfoService;
import com.ioariroi.host.service.HostTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ioariroi
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/host/host-type")
@CrossOrigin  //解决跨域
public class HostTypeController {
    @Autowired
    private HostTypeService hostTypeService;

    @GetMapping
    public List<HostType> list() {
        return hostTypeService.list(null);
    }

    @ApiOperation(value = "新增類別")
    @PostMapping
    public R save(
            @ApiParam(name = "hostType", value = "類別对象", required = true)
            @RequestBody HostType hostType) {

        hostTypeService.save(hostType);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询類別")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "類別ID", required = true)
            @PathVariable String id) {

        HostType hostType = hostTypeService.getById(id);
        return R.ok().data("item", hostType);
    }

    @ApiOperation(value = "根据ID修改類別")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "類別ID", required = true)
            @PathVariable Long id,

            @ApiParam(name = "hostType", value = "類別对象", required = true)
            @RequestBody HostType hostType) {

        hostType.setId(id);
        hostTypeService.updateById(hostType);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable String id){
        return hostTypeService.removeById(id);
    }

}

