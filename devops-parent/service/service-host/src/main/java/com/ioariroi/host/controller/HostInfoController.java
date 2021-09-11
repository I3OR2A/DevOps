package com.ioariroi.host.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioariroi.commonutils.R;
import com.ioariroi.host.entity.HostInfo;
import com.ioariroi.host.query.HostInfoQuery;
import com.ioariroi.host.service.HostInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ioariroi
 * @since 2021-09-04
 */
@RestController
@RequestMapping("/host/host-info")
@CrossOrigin  //解决跨域
public class HostInfoController {
    @Autowired
    private HostInfoService hostInfoService;

    @ApiOperation(value = "所有主機列表")
    @GetMapping("findAll")
    public R findAll() {
        List<HostInfo> list = hostInfoService.list(null);
        return R.ok().data("item", list);
    }

    @ApiOperation(value = "新增主機")
    @PostMapping("addHost")
    public R addHost(
            @ApiParam(name = "hostInfo", value = "主機对象", required = true)
            @RequestBody HostInfo hostInfo) {

        hostInfoService.save(hostInfo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询主機")
    @GetMapping("getHost/{id}")
    public R getHost(
            @ApiParam(name = "id", value = "主機ID", required = true)
            @PathVariable String id) {

        HostInfo hostInfo = hostInfoService.getById(id);
        return R.ok().data("item", hostInfo);
    }

    @ApiOperation(value = "根据ID修改主機")
    @PutMapping("updateHost/{id}")
    public R updateHost(
            @ApiParam(name = "id", value = "主機ID", required = true)
            @PathVariable Long id,

            @ApiParam(name = "hostInfo", value = "主機对象", required = true)
            @RequestBody HostInfo hostInfo) {

        hostInfo.setId(id);
        hostInfoService.updateById(hostInfo);
        return R.ok();
    }

    @ApiOperation(value = "逻辑删除主機")
    @DeleteMapping("deleteHost/{id}")
    public boolean deleteHost(@PathVariable String id){
        return hostInfoService.removeById(id);
    }

//    @ApiOperation(value = "分页主機列表")
//    @GetMapping("{page}/{limit}")
//    public R pageList(
//            @ApiParam(name = "page", value = "当前页码", required = true)
//            @PathVariable Long page,
//
//            @ApiParam(name = "limit", value = "每页记录数", required = true)
//            @PathVariable Long limit){
//
//        Page<HostInfo> pageParam = new Page<>(page, limit);
//
//        hostInfoService.page(pageParam, null);
//        List<HostInfo> records = pageParam.getRecords();
//        long total = pageParam.getTotal();
//
//        return  R.ok().data("total", total).data("rows", records);
//    }

    @ApiOperation(value = "分页主機列表")
    @GetMapping("pageHostCondition/{page}/{limit}")
    public R pageHostCondition(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "hostInfoQuery", value = "查询对象", required = false)
                    HostInfoQuery hostInfoQuery){

        Page<HostInfo> pageParam = new Page<>(page, limit);

        hostInfoService.pageQuery(pageParam, hostInfoQuery);
        List<HostInfo> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }
}

