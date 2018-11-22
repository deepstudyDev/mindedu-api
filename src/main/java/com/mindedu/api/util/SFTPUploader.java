package com.mindedu.api.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SFTPUploader {

    final static Logger logger = LoggerFactory.getLogger(SFTPUploader.class);

    ChannelSftp chSftp = null;
    FileInputStream fis = null;

    public boolean connectSFTP(String host, String userName, int port) {
        long start = System.currentTimeMillis();

        Session ses = null;
        Channel ch = null;
        JSch jsch = new JSch();

        logger.info("connect sftp ------------->" + "host : " + host + " ,userName : " + userName + " ,port : " + port);

        try {
            ses = jsch.getSession(userName, host, port);
            ses.setPassword("akdlsemdpeb12#$");

            Properties prop = new Properties();

            prop.put("StrictHostKeyChecking", "no");
            ses.setConfig(prop);

            ses.connect();

            logger.info("sftp connecting..................");

            ch = ses.openChannel("sftp");

            ch.connect();

            chSftp = (ChannelSftp)ch;

            logger.info("sftp connection success!!!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("sftp connection error!! =========> " + e.toString());
            return false;
        }
        long end = System.currentTimeMillis();

        System.out.println( "실행 시간 : " + ( end - start )/1000.0 );

        return true;
    }

    public String uploadSFTP() {
        String filePath = "/Users/jihoan/Downloads/anjiho.jpg";

        File file = new File(filePath);

        try {
            fis = new FileInputStream(file);
            chSftp.cd("/");
            chSftp.put(fis, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return "1234";
    }

    public static void main(String[] args) {
        SFTPUploader uploader = new SFTPUploader();
        uploader.connectSFTP("211.62.104.179", "root", 6585);
       // uploader.uploadFtp();
    }

}
