package com.exam.file.util;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class TestRSAUtil {

    @Test
    public void testSign() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJH3uGEgYv3cHqzYLtX0MlIjWRbYXuP/BXcq9r7VDETPu1+lQBJhW6UlbaSwEaDWoxZnXrM+c4t0hB3Gl91PDzMCAwEAAQ==";
        String privateKey = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAkfe4YSBi/dwerNgu1fQyUiNZFthe4/8Fdyr2vtUMRM+7X6VAEmFbpSVtpLARoNajFmdesz5zi3SEHcaX3U8PMwIDAQABAkEAiWiHO3d/eLbEcbW4sVSGImiAL09UVJD3liztxstMF2F7tseFuV69cS0YR/bw+tuesSJWT5vHSiIS83u8tOOdMQIhAOo4MYs4+5OaphM8N3dFN4nK1YCs5/h5F9AEy3Hs/G9/AiEAn4qcpLhy0viegGvo/iNNQ5V6BKgtPTpO1vJwAYdBek0CIQCTUGy46EozeF1kU8d/GOXpoM3QdPAh8+fqSlm7ehb7+QIhAJjjaIGiEMeEYcCHqNwCUIS3thrIX7IRMoRiCFwuldzxAiEAzcXgPMqciKGW1FYAZFaBQ0lROx/m3Ezhwwv6rGoH/FQ=";

        String sign = RSAUtil.executeSignature(privateKey,"9a83d40f28e15ee2ce6b41a91776957c");
        System.out.println(sign);

        boolean result = RSAUtil.verifySignature(publicKey,sign,"9a83d40f28e15ee2ce6b41a91776957c");
        System.out.println(result);
    }
}
