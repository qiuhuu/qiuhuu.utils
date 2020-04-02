package com.qiuhuu.utils.code.controller;

import com.qiuhuu.utils.code.gif.utils.Captcha;
import com.qiuhuu.utils.code.gif.utils.GifCaptcha;
import com.qiuhuu.utils.code.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @author qiuhuu
 * @Create: 2020-03-10 11:42
 */
@Slf4j
@RestController
@RequestMapping("code")
public class CodeController {

    /**
     * 获取验证码（Gif版本）
     * @param response
     */
    @GetMapping("gif")
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

    @GetMapping(value = "/image")
    public void getCode(HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");
            //禁止图像缓存
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            Image image = new Image(100,32);
            System.out.println(image.getCode());
            image.write(response.getOutputStream());
        } catch (IOException e) {
            System.out.println("验证码获取失败");
            e.printStackTrace();
        }
    }

}
