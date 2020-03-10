package com.qiuhuu.utils.gitcode.controller;

import com.qiuhuu.utils.gitcode.utils.Captcha;
import com.qiuhuu.utils.gitcode.utils.GifCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: qiuhuu
 * @Create: 2020-03-10 11:42
 */
@Slf4j
@RestController
public class CodeController {

    /**
     * 获取验证码（Gif版本）
     * @param response
     */
    @GetMapping("code")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request){
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            HttpSession session = request.getSession(true);

            Captcha captcha = new GifCaptcha(146,33,4);
            /* 输出 */
            captcha.out(response.getOutputStream());
            String vcodeText = captcha.text().toLowerCase();
            //存入Session
            session.setAttribute("_code",vcodeText);
            log.info("保存vcode:"+vcodeText);

            System.out.println("------"+session.getAttribute("_code"));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取验证码异常："+e.getMessage());
        }
    }

}
