package com.qiuhuu.utils;

import com.qiuhuu.utils.redis.utils.RedisUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests{

    @Autowired
    private RedisUtils redisUtils;

    private static int THREAD_COUNT = 5;
    @Test
    void contextLoads() throws IOException {
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Threads(redisUtils);
            thread.setName("myThread--"+i);
            threadList.add(thread);
        }
        threadList.forEach(var->{var.start();});

        int read = System.in.read();
    }

}
@Component
class Threads extends Thread {

    private RedisUtils redisUtils;

    public Threads(RedisUtils redisUtils){
        // 通过构造函数从外部引入
        this.redisUtils  = redisUtils;
    }
    @SneakyThrows
    @Override
    public void run() {
        for (int i = 1; i <= 100000; i++) {
            String key = "asd";
            redisUtils.incr(key,1);
            System.out.println(Thread.currentThread().getName()+" 线程："+i+"");
        }
    }
}
