package com.ioariroi.host.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioariroi.host.entity.HostInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioariroi.host.query.HostInfoQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ioariroi
 * @since 2021-09-04
 */
public interface HostInfoService extends IService<HostInfo> {
    void pageQuery(Page<HostInfo> pageParam, HostInfoQuery teacherQuery);}
