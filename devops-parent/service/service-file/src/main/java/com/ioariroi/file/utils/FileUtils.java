package com.ioariroi.file.utils;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.Configuration;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private final static String tmpfilepath = System.getProperty("java.io.tmpdir");

    /**
     * 取得 SMB2/3 認證
     *
     * @return
     */
    public static CIFSContext getAuth(String domain, String username, String password) throws CIFSException {
        Properties prop = new Properties();
        prop.put("jcifs.smb.client.enableSMB2", "true");
        prop.put("jcifs.smb.client.disableSMB1", "false");
        prop.put("jcifs.traceResources", "true");
        Configuration config = new PropertyConfiguration(prop);
        CIFSContext baseContext = new BaseContext(config);
        CIFSContext contextWithCred = baseContext.withCredentials(new NtlmPasswordAuthenticator(domain, username, password));

        return contextWithCred;
    }

    /**
     * 删除本地磁盤中的文件
     *
     * @param file
     * @return
     */
    public static boolean removeFile(File file) {
        if (file.isFile() && file.exists()) {
            logger.info("刪除文件 ：" + file.getPath());
            return file.delete();
        }
        return false;
    }

    /**
     * 把本地磁盤中的文件從局域網共享文件中刪除
     *
     * @param remoteFile
     * @return
     */
    public static boolean removeFile(SmbFile remoteFile) {
        try {
            if (remoteFile.isFile() && remoteFile.exists()) {
                logger.info("刪除遠程文件 ：" + remoteFile.getPath());
                remoteFile.delete();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static SmbFile saveSMB(MultipartFile file, String smbfile, CIFSContext context) {
        SmbFile rmifile = null;
        InputStream fis = null;
        SmbFileOutputStream smbfos = null;

        try {
            rmifile = new SmbFile(smbfile, context);
            fis = file.getInputStream();
            smbfos = new SmbFileOutputStream(rmifile);

            byte[] content = new byte[1024];
            int len = 0;
            while ((len = fis.read(content, 0, content.length)) > 0) {
                smbfos.write(content, 0, len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (smbfos != null) {
                    smbfos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rmifile;
    }

    /**
     * 下载文件到临时文件夹
     *
     * @param smbfile
     * @param tmpfilepath
     * @return 下载后的文件地址
     */
    public static File saveSMB(String smbfile, String tmpfilepath, CIFSContext context) {
        File localfile = null;
        InputStream bis = null;
        OutputStream bos = null;
        try {
            SmbFile rmifile = new SmbFile(smbfile, context);
            String filename = rmifile.getName();
            bis = new BufferedInputStream(new SmbFileInputStream(rmifile));
            localfile = new File(tmpfilepath + File.separator + filename);
            bos = new BufferedOutputStream(new FileOutputStream(localfile));
            double length = rmifile.getContentLengthLong();
            logger.info("缓存文件大小=" + length / (1024 * 1024) + "//MB");
            byte[] buffer = new byte[(int) length];
            Date date = new Date();
            bis.read(buffer);
            bos.write(buffer);
            Date end = new Date();
            int time = (int) ((end.getTime() - date.getTime()) / 1000);
            if (time > 0)
                logger.info("用时:" + time + "秒 " + "速度:" + length / time / 1024 + "kb/秒");
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            try {
                bos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return localfile;
    }

    /**
     * @param dir local path File
     * @return local path list
     * @throws Exception
     * @author coco1
     */
    public final static List<String> showAllFiles(File dir) throws Exception {
        List<String> returnDir = new ArrayList<String>();
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {

            if (fs[i].isDirectory()) {
                try {
                    returnDir.addAll(showAllFiles(fs[i]));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                returnDir.add(fs[i].getAbsolutePath());
            }
        }
        return returnDir;
    }

    /**
     * @param fs2 SmbFile fs2
     * @return List of remote file system
     * @throws SmbException
     * @author coco1
     */
    public final static List<String> showAllFiles(SmbFile fs2) throws SmbException {
        List<String> returnDir = new ArrayList<String>();
        SmbFile[] fs = fs2.listFiles();
        for (int i = 0; i < fs.length; i++) {

            if (fs[i].isDirectory()) {
                try {

                    returnDir.addAll(showAllFiles(fs[i]));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {

                returnDir.add(fs[i].getPath());
            }
        }
        return returnDir;
    }

    public static void main(String[] args) throws Exception {
    }
}

