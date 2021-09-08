package com.ioariroi.host.controller;


import com.ioariroi.commonutils.R;
import com.ioariroi.host.entity.HostAccount;
import com.ioariroi.host.entity.HostType;
import com.ioariroi.host.service.HostAccountService;
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
@RequestMapping("/host/host-account")
public class HostAccountController {
    @Autowired
    private HostAccountService hostAccountService;

    @GetMapping
    public List<HostAccount> list() {
        return hostAccountService.list(null);
    }

    @ApiOperation(value = "新增帳號")
    @PostMapping
    public R save(
            @ApiParam(name = "hostAccount", value = "帳號对象", required = true)
            @RequestBody HostAccount hostAccount) {

        hostAccountService.save(hostAccount);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询帳號")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "帳號ID", required = true)
            @PathVariable String id) {

        HostAccount hostAccount = hostAccountService.getById(id);
        return R.ok().data("item", hostAccount);
    }

    @ApiOperation(value = "根据ID修改帳號")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "帳號ID", required = true)
            @PathVariable Long id,

            @ApiParam(name = "hostAccount", value = "帳號对象", required = true)
            @RequestBody HostAccount hostAccount) {

        hostAccount.setId(id);
        hostAccountService.updateById(hostAccount);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable String id){
        return hostAccountService.removeById(id);
    }

}

