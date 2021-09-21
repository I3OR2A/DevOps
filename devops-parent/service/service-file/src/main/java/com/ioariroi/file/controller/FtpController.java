package com.ioariroi.file.controller;

import com.ioariroi.commonutils.R;
import com.ioariroi.file.entity.SmbEntity;
import com.ioariroi.file.utils.FTPUtils;
import com.ioariroi.file.utils.FileUtils;
import jcifs.CIFSException;
import jcifs.smb.SmbFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin //跨域
@RestController
@RequestMapping("/ftp")
public class FtpController {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @GetMapping(value = "/listRemoteFiles")
    public R listRemoteFiles(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return R.ok();
    }

    @GetMapping(value = "/createRemoteFile")
    public R createRemoteFile(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return R.ok();
    }

    @GetMapping(value = "/createRemoteDir")
    public R createRemoteDir(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return R.ok();
    }

    @GetMapping(value = "/deleteRemoteFile")
    public R deleteRemoteFile(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return R.ok();
    }

    @GetMapping(value = "/deleteRemoteDir")
    public R deleteRemoteDir(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return R.ok();
    }

    @PostMapping(value = "/uploadRemoteFile")
    public R uploadRemoteFile(@RequestParam String hostname, @RequestParam String account, @RequestParam String path, @RequestParam("file") MultipartFile file) {
        return R.ok();
    }

    @GetMapping(value = "/downloadRemoteFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> downloadRemoteFile(@RequestParam String hostname, @RequestParam String account, @RequestParam String path) {
        return ResponseEntity.notFound().build();
    }
}
