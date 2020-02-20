package com.exam.file.controller;

import com.exam.file.filter.AuthorizationFilter;
import com.exam.file.util.RSAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public void create(HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String sid = request.getHeader("X-SID");
        String privateKey = request.getHeader("priKey");
        String sign = RSAUtil.executeSignature(privateKey,sid);
        response.setHeader("X-Signature",sign);
    }
}
