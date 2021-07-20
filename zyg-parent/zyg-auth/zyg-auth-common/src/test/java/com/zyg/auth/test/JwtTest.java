package com.zyg.auth.test;

import com.zyg.auth.entity.UserInfo;
import com.zyg.auth.utils.JwtUtils;
import com.zyg.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjgsInVzZXJuYW1lIjoiemhhbmdzYW4iLCJleHAiOjE2MjY2ODU4NDR9.SmoHGuQSzfuOCNLUWNU1GRdrtALhOMpc6i8ZEyY2ciW6XjldM7pQfp2lBAktLFrKNOT69jpjddVNztfRqlwL7cryCpmbnVzzETzgUxw0jE1rcSBYaoldaWtkxyEVRxXgkoBeKOlXk6HSXYZ3MEOiiF5BsDJGpjexX-mXvIQe_9Y";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
