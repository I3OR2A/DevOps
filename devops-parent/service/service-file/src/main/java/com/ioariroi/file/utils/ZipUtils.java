package com.ioariroi.file.utils;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.CloseableIterator;
import jcifs.Configuration;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.ioariroi.file.utils.FileUtils.getAuth;

@Slf4j
@Component
public class ZipUtils {

    /**
     * 将文件或是文件夹打包压缩成zip格式
     *
     * @author ysc
     */

//    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    public ZipUtils() {
    }

    public static SmbFile unzipSmbZip(String sourcePath, String unzipPath, CIFSContext context) {
        SmbFile smbFile = null;
        SmbFile rmiFile = null;
        SmbFile parFile = null;
        SmbFileOutputStream smbfos = null;
        SmbFileInputStream in = null;
        ZipArchiveInputStream zip = null;

        ZipFile zipfile = null;

        try {
            smbFile = new SmbFile(sourcePath, context); // zipFile是一个包含服务器IP和路径的完整url

            System.out.println("smbFile:--" + sourcePath);
            in = new SmbFileInputStream(smbFile);
            zip = new ZipArchiveInputStream(in, "ms950");

            ZipArchiveEntry entry = null;
            while ((entry = zip.getNextZipEntry()) != null) {
                System.out.println(entry.getName());
                if (entry.isDirectory()) {
                    // 創建目錄
                    rmiFile = new SmbFile(unzipPath + entry.getName(), context);
                    System.out.println("這是資料夾:" + rmiFile.getPath());
                    if (!rmiFile.exists()) {
                        rmiFile.mkdirs();
                    }
                } else {
                    // 創建檔案
                    rmiFile = new SmbFile(unzipPath + entry.getName(), context);
                    // 有些 ZIP 只會有檔案路徑紀錄不會記錄資料夾路徑，創建檔案所在目錄
                    parFile = new SmbFile(rmiFile.getParent(), context);
                    if (!parFile.exists()) {
                        parFile.mkdirs();
                    }
                    smbfos = new SmbFileOutputStream(rmiFile);

                    byte[] content = new byte[1024];
                    int len;
                    while ((len = zip.read(content)) != -1) {
                        smbfos.write(content, 0, len);
                    }
                    smbfos.flush();
                    smbfos.close();
                }
                rmiFile.setLastModified(entry.getTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (smbfos != null) {
                    smbfos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rmiFile;
    }

    /**
     * 创建ZIP文件
     *
     * @param sourcePath 文件或文件夹路径
     * @param zipPath    生成的zip文件存在路径（包括文件名）
     * @throws MalformedURLException
     * @throws UnknownHostException
     * @throws SmbException
     */
    public static SmbFile createSmbZip(String sourcePath, String zipPath, CIFSContext context) throws IOException {
        SmbFileOutputStream fos = null;
        ZipArchiveOutputStream zos = null;
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile(zipPath, context);
            fos = new SmbFileOutputStream(smbFile);
            zos = new ZipArchiveOutputStream(fos);
            // zos.setEncoding("ms950");
            writeSmbZip(new SmbFile(sourcePath, context), "", zos);

            zos.closeArchiveEntry();
            zos.flush();
            zos.finish();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }

        return smbFile;
    }

    public static SmbFile createSmbZipFromMultiSrc(List<String> sourcePaths, String zipPath, CIFSContext context) throws IOException {
        SmbFileOutputStream fos = null;
        ZipArchiveOutputStream zos = null;
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile(zipPath, context);
            fos = new SmbFileOutputStream(smbFile);
            zos = new ZipArchiveOutputStream(fos);
            // zos.setEncoding("ms950");
            for (String sourcePath : sourcePaths) {
                writeSmbZip(new SmbFile(sourcePath, context), "", zos);
            }
            zos.closeArchiveEntry();
            zos.flush();
            zos.finish();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }

        return smbFile;
    }

    private static void writeSmbZip(SmbFile smbFile, String parentPath, ZipArchiveOutputStream zos) {
        try {
            if (smbFile.exists()) {
                if (smbFile.isDirectory()) {//处理文件夹
                    parentPath += smbFile.getName();
                    System.out.println("parentPath: " + parentPath);
                    // 獲取資料夾下的所有子檔案
                    CloseableIterator ci = smbFile.children();
//                    SmbFile[] smbFiles = smbFile.listFiles();
                    while (ci.hasNext()) {
                        SmbFile childrenFile = (SmbFile) ci.next();
                        System.out.println("childrenFile: " + childrenFile.getName());
//                        System.out.println(f.getPath());
//                        System.out.println(f.getParent());
                        writeSmbZip(childrenFile, parentPath, zos);
                    }
                } else {
                    SmbFileInputStream fis = null;
                    DataInputStream dis = null;
                    try {
                        System.out.println("檔案名稱: " + smbFile.getName());
                        System.out.println("路徑名稱: " + smbFile.getPath());
                        System.out.println("父目錄名稱: " + smbFile.getParent());
                        fis = new SmbFileInputStream(smbFile);
                        dis = new DataInputStream(new BufferedInputStream(fis));
                        ZipArchiveEntry ze = new ZipArchiveEntry(filter(parentPath + smbFile.getName()));
                        ze.setTime(smbFile.lastModified());
                        System.out.println("realfilePath: " + parentPath + smbFile.getName());
                        zos.putArchiveEntry(ze);
                        byte[] content = new byte[1024];
                        int len;
                        while ((len = dis.read(content)) != -1) {
                            zos.write(content, 0, len);
                            zos.flush();
                        }

                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    } finally {
                        try {
                            if (dis != null) {
                                dis.close();
                            }
                        } catch (IOException e) {
                            log.error("创建ZIP文件失败", e);
                        }
                    }
                }
            }
        } catch (SmbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CIFSException e) {
            e.printStackTrace();
        }
    }

    public static void createZip(String sourcePath, String zipPath) throws MalformedURLException, FileNotFoundException, SmbException, UnknownHostException {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {//处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                for (File f : files) {
                    writeZip(f, parentPath, zos);
                }
            } else {
                FileInputStream fis = null;
                DataInputStream dis = null;
                try {
                    fis = new FileInputStream(file);
                    dis = new DataInputStream(new BufferedInputStream(fis));
                    ZipEntry ze = new ZipEntry(filter(parentPath + file.getName()));
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (dis != null) {
                            dis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException, FileNotFoundException, SmbException, UnknownHostException {
//	        ZipUtils.createSmbZip("smb://biggrab:123456@192.168.1.111/biggrab/export/2016-03-06/澳门特别行政区/","smb://biggrab:123456@192.168.1.111/biggrab/export/2016-03-06/test.zip");
        System.out.println(filter("2014-11-22/\\Exception/\\Exception.json"));
    }

    public static String filter(String str) {
        String ret = str.replaceAll("\\\\", "");
        return ret;
    }


}