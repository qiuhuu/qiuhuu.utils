package com.qiuhuu.utils.redis.controller;

import com.qiuhuu.utils.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: qiuhuu
 * @Create: 2020-03-10 18:09
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @PutMapping
    public String setRedis(String key,String value,int indexdb){
        // redisUtils.set(key,value,300L);
        // long l = redisUtils.longIncr(key, 2222222222222L);
        // return "dsa";
        return redisUtils.set(key, value, indexdb, 200)?"成功":"失败";
    }
}
