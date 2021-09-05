package com.ioariroi.host.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioariroi.host.entity.HostInfo;
import com.ioariroi.host.mapper.HostInfoMapper;
import com.ioariroi.host.query.HostInfoQuery;
import com.ioariroi.host.service.HostInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ioariroi
 * @since 2021-09-04
 */
@Service
public class HostInfoServiceImpl extends ServiceImpl<HostInfoMapper, HostInfo> implements HostInfoService {
    @Override
    public void pageQuery(Page<HostInfo> pageParam, HostInfoQuery hostInfoQuery) {

        QueryWrapper<HostInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("sort");

        if (hostInfoQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        Long typeId = hostInfoQuery.getTypeId();
        String hostname = hostInfoQuery.getHostname();
        String ipAddress = hostInfoQuery.getIpAddress();

        if (!StringUtils.isEmpty(typeId)) {
            queryWrapper.eq("typeId", typeId);
        }

        if (!StringUtils.isEmpty(hostname) ) {
            queryWrapper.like("hostname", hostname);
        }

        if (!StringUtils.isEmpty(ipAddress)) {
            queryWrapper.like("ipAddress", ipAddress);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
}
