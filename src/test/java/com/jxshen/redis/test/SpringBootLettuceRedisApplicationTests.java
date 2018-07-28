package com.jxshen.redis.test;

import com.jxshen.redis.test.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * @author jxshen on 2018/07/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLettuceRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisCacheTemplate;

    @Test
    public void test() {
        ExecutorService threadPool = Executors.newFixedThreadPool(1000);
        List<Future<?>> futureList = new ArrayList<>();
        IntStream.range(0, 1000).forEach(i -> {
            log.info("" + i);
            futureList.add(threadPool.submit(
                () -> stringRedisTemplate.opsForValue().increment("kk", 1)));});
        stringRedisTemplate.opsForValue().set("k1", "v1");
        final String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.info(k1);
        String key = "user:1:jxshen:2008";
        UserEntity user = UserEntity.builder()
                .id(1L).name("jxshen").birth(new Date()).build();
        redisCacheTemplate.opsForValue().set(key, user);
        final UserEntity cachedUser = (UserEntity) redisCacheTemplate.opsForValue().get(key);
        log.info("[{}]", cachedUser);
        futureList.forEach(Future::isDone);

    }

}
