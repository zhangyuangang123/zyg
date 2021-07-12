/*
package com.zyg.auth.test;

import com.zyg.auth.entity.UserInfo;
import com.zyg.auth.utils.JwtUtils;
import com.zyg.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "/Users/mac/rsa.pub";

    private static final String priKeyPath = "/Users/mac/rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    */
/*@Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }*//*


    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTYxNjU0NzcxMX0.BLKaiMRFVkLTyOfPb1iynRjoFc4vbIpZvg1BLDqClgoIcLQmMJQ7JKU99HFUc5bl5mzqX6kF3wj0fghftTb2WhGlajcLmeFVdOfjVqN3wKS0cwBe0la6AgeoPeL_8ozp_i27t7rlajaTte-SDYQ9o0kru_Gbl_oDssS-9lKT5S8";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }

    public static void main(String[] args) throws IOException {
        File f = new File("/Users/mac/Java.txt");
        f.createNewFile();
    }
}*/
