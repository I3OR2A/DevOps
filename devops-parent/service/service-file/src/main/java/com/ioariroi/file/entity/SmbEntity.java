package com.ioariroi.file.entity;

import lombok.Data;

@Data
public class SmbEntity {
    private String name;
    private String parent;
    private String path;
    private long size;
    private long createTime;
    private long lastModified;
    private long lastAccess;
    private boolean isDirectory;
    private boolean isHidden;
}
