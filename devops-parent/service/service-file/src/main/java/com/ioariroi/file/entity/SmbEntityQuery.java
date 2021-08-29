package com.ioariroi.file.entity;

import lombok.Data;

import java.util.List;

@Data
public class SmbEntityQuery {
    private String hostname;
    private String account;
    private String path;
    private List<SmbEntity> smbEntities;
}
