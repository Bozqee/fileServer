package com.exam.file.util;

import org.junit.Test;

import java.io.File;

public class TestAESUtil {

    @Test
    public void testEncrypt() {
        File file = new File("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\86749fadf507c375ff35a0d9b40068d7.jpg");
        String privateKey = "b63c285e81844690a3db8ea08ba1a1e32860082372980765967";
        String fileName = UUIDUtil.getUuid();
        File encryptFile = AESUtil.encrypt(file,fileName,".jpg",privateKey);
        File endDirection=new File("C:\\Users\\Administrator\\Desktop");

        File endFile=new File(endDirection+ File.separator+ encryptFile.getName());
        try {
            encryptFile.renameTo(endFile);
        }catch(Exception e) {

        }
    }
    @Test
    public void testDecrypt() {
        File file = new File("C:\\Users\\Administrator\\Desktop\\6da58a466c684bac84d5ec533816f8455262846324407641659.jpg");
        String privateKey = "b63c285e81844690a3db8ea08ba1a1e32860082372980765967";
        String fileName = UUIDUtil.getUuid();
        File decryptFile = AESUtil.decrypt(file,fileName,".jpg",privateKey);
        File endDirection=new File("C:\\Users\\Administrator\\Desktop");
        File endFile=new File(endDirection+ File.separator+ decryptFile.getName());
        try {
            decryptFile.renameTo(endFile);
        }catch(Exception e) {

        }

    }
}
