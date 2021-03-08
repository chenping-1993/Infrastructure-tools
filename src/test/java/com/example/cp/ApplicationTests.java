package com.example.cp;

import com.example.cp.common.tool.RedisUtil;
import com.example.cp.entity.City;
import com.whalin.MemCached.MemCachedClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private MemCachedClient memCachedClient;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() throws InterruptedException{
        // 放入缓存
        boolean flag = memCachedClient.set("a", 1);

        // 取出缓存
        Object a = memCachedClient.get("a");
        System.out.println(a);


        // 3s后过期
        memCachedClient.set("b", "2", new Date(3000));
        Object b = memCachedClient.get("b");
        System.out.println(b);

        Thread.sleep(3000);
        b = memCachedClient.get("b");
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testRedis() throws InterruptedException{
        System.out.println(redisUtil.zsetGetAll("debug:game:free_room_list"));

    }

    @Test
    public void testRedisSetObject() throws InterruptedException{
        City city = new City();
        city.setCityName("xian");
        city.setPopulation(22222);
        city.setProvince("shanxi");
        city.setRank(3);
        redisUtil.setObject("xa",city);

        City xa = redisUtil.getObject("xa", City.class);
        System.out.println(xa);

    }

    @Test
    public void testRedisGetList() throws InterruptedException{
        City city = new City();
        city.setCityName("xian");
        city.setPopulation(22222);
        city.setProvince("shanxi");
        city.setRank(3);

        City city1 = new City();
        city1.setCityName("jn");
        city1.setPopulation(33333);
        city1.setProvince("sd");
        city1.setRank(3);

        List<City> list = new ArrayList<>();
        list.add(city);
        list.add(city1);

        redisUtil.setObject("testList",list);

        List<City> xa = redisUtil.getList("testList", City.class);
        System.out.println(xa);

    }
}
