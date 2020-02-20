package com.exam.file.filter;

import com.exam.file.exception.BadRequestException;
import com.exam.file.util.JDBCUtil;
import com.exam.file.util.RSAUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.internal.ExactComparisonCriteria;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

public class AuthorizationFilter implements Filter {

    private static Logger log = Logger.getLogger(Test.class.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        InputStream in = AuthorizationFilter.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            log.info("读取配置文件失败");
            throw new BadRequestException("读取配置文件失败！");
        }
        String publicKey = properties.getProperty("server.publicKey");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        // 授权直接通过
        if(req.getRequestURI().contains("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if(req.getRequestURI().contains("/file/")){
            //验证
            String sid = req.getHeader("X-SID");  //原来数据
            String signature = req.getHeader("X-Signature");
            if (sid == null || signature == null) {
                log.info("请先授权");
                throw new BadRequestException(HttpStatus.FORBIDDEN, "请先授权");
            }
            try {
                boolean result = RSAUtil.verifySignature(publicKey, signature, sid);
                if (result) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    log.info("请先授权");
                    throw new BadRequestException(HttpStatus.FORBIDDEN, "请先授权");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
