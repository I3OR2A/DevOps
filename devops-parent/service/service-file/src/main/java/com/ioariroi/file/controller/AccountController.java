package com.ioariroi.file.controller;

import com.ioariroi.commonutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin //跨域
@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/{account}")
    public R getById(@PathVariable String account) {
        return R.ok().data(account, "nino0506");
    }
}
