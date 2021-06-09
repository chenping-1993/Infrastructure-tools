package com.example.cp.common.tool;

import com.alibaba.fastjson.JSON;
import com.example.cp.entity.Tuples;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.util.*;
import org.apache.commons.lang3.RandomUtils;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Service
@Slf4j
public class RedisUtil {
    @Autowired
    private JedisPool jedisPool;

    public RedisUtil() {
    }

    public RedisUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public long expire(String key, int seconds) {
        if (key != null && !key.equals("")) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                long var4 = jedis.expire(key, seconds);
                return var4;
            } catch (Exception var9) {
                log.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + var9.getMessage(), var9);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return 0L;
        } else {
            return 0L;
        }
    }

    //订阅
    public void subscribeMsg(JedisPubSub jedisPubSub, String channels) {
        try {
            Jedis jedis = null;
            jedis = this.jedisPool.getResource();
            jedis.subscribe(jedisPubSub, channels);
        } catch (Exception e) {
        }
    }

    //发布
    public Long publishMsg(String channel, String message) {
        Long result = -1L;
        try {
            Jedis jedis = null;
            jedis = this.jedisPool.getResource();
            result = jedis.publish(channel,message);
            return result;
        } catch (Exception e) {
        }
        return result;
    }


    public long expireAt(String key, int unixTimestamp) {
        if (key != null && !key.equals("")) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                long var4 = jedis.expireAt(key, (long)unixTimestamp);
                return var4;
            } catch (Exception var9) {
                log.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + var9.getMessage(), var9);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return 0L;
        } else {
            return 0L;
        }
    }

    /**
     * @Description:  截取list集合
     * @param: key
     * @param: start
     * @param: end
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2020/9/17 16:59
     */
    public String trimList(String key, long start, long end) {
        if (key != null && !key.equals("")) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                String var7 = jedis.ltrim(key, start, end);
                return var7;
            } catch (Exception var11) {
                log.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + var11.getMessage(), var11);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return "-";
        } else {
            return "-";
        }
    }

    /**
     * @Description:  set 获取指定key的值的数量
     * @param: key
     * @return: long
     * @Author: chenping
     * @Date: 2020/9/17 16:06
     */
    public long setLength(String key) {
        if (key == null) {
            return 0L;
        } else {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                long var3 = jedis.scard(key);
                return var3;
            } catch (Exception var8) {
                log.error("countSet error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return 0L;
        }
    }

    /**
     * @Description:  set一个或者多个数据并设置过期时间
     * @param: key
     * @param: seconds 过期时间
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 16:08
     */
    public boolean setAddWithTime(String key, int seconds, String... value) {
        boolean result = this.setAdd(key, value);
        if (result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    /**
     * @Description:  set一个或者多个数据
     * @param: key
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 16:09
     */
    public boolean setAdd(String key, String... value) {
        if (key != null && value != null) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                jedis.sadd(key, value);
                boolean var4 = true;
                return var4;
            } catch (Exception var8) {
                log.error("setList error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * @Description:  修改list指定索引的值
     * @param: key
     * @param: index 索引
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 16:12
     */
    public boolean listSetIndexValue(String key,Long index, String value) {
        if (key != null && value != null && index != null) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                jedis.lset(key,index, value);
                boolean var4 = true;
                return var4;
            } catch (Exception var8) {
                log.error("lset error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * @Description:  判断是否是set集合的元素
     * @param: key
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 16:27
     */
    public boolean isSetMember(String key, String value) {
        if (key != null && value != null) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                boolean var4 = jedis.sismember(key, value);
                return var4;
            } catch (Exception var8) {
                log.error("setList error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * @Description:  获取指定key的set集合
     * @param: key
     * @return: java.util.Set<java.lang.String>
     * @Author: chenping
     * @Date: 2020/9/17 16:29
     */
    public Set<String> setMembers(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Set var3 = jedis.smembers(key);
            return var3;
        } catch (Exception var7) {
            log.error("getList error.", var7);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }

    /**
     * @Description:  移除set集合中一个或多个成员
     * @param: key
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 16:31
     */
    public boolean setRemove(String key, String... value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.srem(key, value);
            boolean var4 = true;
            return var4;
        } catch (Exception var8) {
            log.error("getList error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    /**
     * @Description:  返回set集合中一个或多个随机数
     * @param: key
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2020/9/17 16:32
     */
    public String setReturnRandomValue(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            String var3 = jedis.srandmember(key);
            return var3;
        } catch (Exception var7) {
            log.error("getList error.", var7);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return "";
    }

    /**
     * @Description:  删除list集合的数据
     * @param: key
     * @param: values
     * @return: int
     * @Author: chenping
     * @Date: 2020/9/17 16:49
     */
    public int removeListValue(String key, List<String> values) {
        return this.removeListValue(key, 1L, values);
    }

    public int removeListValue(String key, long count, List<String> values) {
        int result = 0;
        if (values != null && values.size() > 0) {
            Iterator var6 = values.iterator();

            while(var6.hasNext()) {
                String value = (String)var6.next();
                if (this.removeListValue(key, count, value)) {
                    ++result;
                }
            }
        }

        return result;
    }

    public boolean removeListValue(String key, long count, String value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.lrem(key, count, value);
            boolean var6 = true;
            return var6;
        } catch (Exception var10) {
            log.error("getList error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    public List<String> rangeList(String key, long start, long end) {
        if (key != null && !key.equals("")) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                List var7 = jedis.lrange(key, start, end);
                return var7;
            } catch (Exception var11) {
                log.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + var11.getMessage(), var11);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * @Description:  获取list集合指定key的集合长度
     * @param: key
     * @return: long
     * @Author: chenping
     * @Date: 2020/9/17 17:03
     */
    public long listLen(String key) {
        if (key == null) {
            return 0L;
        } else {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                long var3 = jedis.llen(key);
                return var3;
            } catch (Exception var8) {
                log.error("countList error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return 0L;
        }
    }

    public boolean lpush(String key, int seconds, String... value) {
        boolean result = this.lpush(key, value);
        if (result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    /**
     * @Description:  list集合放入数据
     * @param: key
     * @param: value
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 17:04
     */
    public boolean lpush(String key, String... value) {
        if (key != null && value != null) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                jedis.lpush(key, value);
                boolean var4 = true;
                return var4;
            } catch (Exception var8) {
                log.error("setList error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * @Description:  获取list集合指定索引的数据
     * @param: key
     * @param: index
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2020/9/17 17:05
     */
    public String lindex(String key, int index) {
        if (key == null) {
            return "";
        } else {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                String var4 = jedis.lindex(key, (long)index);
                return var4;
            } catch (Exception var8) {
                log.error("lindex error.", var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return "";
        }
    }

    public boolean lpush(String key, List<String> list) {
        if (key != null && list != null && list.size() != 0) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                String value = (String)var3.next();
                this.lpush(key, value);
            }

            return true;
        } else {
            return false;
        }
    }

    public List<String> lrange(String key) {
        return this.lrange(key, 0, -1);
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            List var5 = jedis.lrange(key, (long)start, (long)end);
            return var5;
        } catch (Exception var9) {
            log.error("lrange error.", var9);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }

    public byte[] rpop(byte[] key) {
        byte[] bytes = null;
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            bytes = jedis.rpop(key);
        } catch (Exception var8) {
            log.error("setList error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return bytes;
    }

    public List<String> brpop(int seconds, String key) {
        List<String> result = null;
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            result = jedis.brpop(seconds, key);
        } catch (Exception var9) {
            log.error("setList error.", var9);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return result;
    }

    /**
     * @Description:  移除列表的最后一个元素，返回值为移除的元素
     * @param: key
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2020/9/17 17:09
     */
    public String rpop(String key) {
        String data = null;
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            data = jedis.rpop(key);
        } catch (Exception var8) {
            log.error("setList error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return data;
    }

    public long ttl(String key) {
        if (key != null && !key.equals("")) {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                long var3 = jedis.ttl(key);
                return var3;
            } catch (Exception var8) {
                log.error("EXPIRE error[key=" + key + "]" + var8.getMessage(), var8);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return 0L;
        } else {
            return 0L;
        }
    }

    public boolean hashSet(String domain, String key, String value) {
        if (value == null) {
            return false;
        } else {
            Jedis jedis = null;

            try {
                jedis = this.jedisPool.getResource();
                jedis.hset(domain, key, value);
                boolean var5 = true;
                return var5;
            } catch (Exception var9) {
                log.error("setHSet error.", var9);
                this.returnBrokenResource(jedis);
            } finally {
                this.returnResource(jedis);
            }

            return false;
        }
    }

    public String hashGet(String domain, String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            String var4 = jedis.hget(domain, key);
            return var4;
        } catch (Exception var8) {
            log.error("getHSet error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }

    public long hashDelete(String domain, String key) {
        Jedis jedis = null;
        long count = 0L;

        try {
            jedis = this.jedisPool.getResource();
            count = jedis.hdel(domain, new String[]{key});
        } catch (Exception var10) {
            log.error("delHSet error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return count;
    }

    public long hashDelete(String domain, String... key) {
        Jedis jedis = null;
        long count = 0L;

        try {
            jedis = this.jedisPool.getResource();
            count = jedis.hdel(domain, key);
        } catch (Exception var10) {
            log.error("delHSet error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return count;
    }

    public boolean hashExists(String domain, String key) {
        Jedis jedis = null;
        boolean isExist = false;

        try {
            jedis = this.jedisPool.getResource();
            isExist = jedis.hexists(domain, key);
        } catch (Exception var9) {
            log.error("existsHSet error.", var9);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return isExist;
    }

    public List<Map.Entry<String, String>> scanHSet(String domain, String match) {
        Jedis jedis = null;

        try {
            int cursor = 0;
            jedis = this.jedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(match);
            ArrayList list = new ArrayList();

            do {
                ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(domain, String.valueOf(cursor), scanParams);
                list.addAll(scanResult.getResult());
                cursor = Integer.parseInt(scanResult.getStringCursor());
            } while(cursor > 0);

            ArrayList var8 = list;
            return var8;
        } catch (Exception var12) {
            log.error("scanHSet error.", var12);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }

    /**
     * @Description:  获取hash 所有的value值
     * @param: domain
     * @return: java.util.List<java.lang.String>
     * @Author: chenping
     * @Date: 2020/9/17 17:31
     */
    public List<String> hashValues(String domain) {
        Jedis jedis = null;
        List retList = null;

        try {
            jedis = this.jedisPool.getResource();
            retList = jedis.hvals(domain);
        } catch (Exception var8) {
            log.error("hvals error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return retList;
    }

    public Set<String> hashKeys(String domain) {
        Jedis jedis = null;
        Set retList = null;

        try {
            jedis = this.jedisPool.getResource();
            retList = jedis.hkeys(domain);
        } catch (Exception var8) {
            log.error("hkeys error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return retList;
    }


    public long hashLength(String domain) {
        Jedis jedis = null;
        long retList = 0L;

        try {
            jedis = this.jedisPool.getResource();
            retList = jedis.hlen(domain);
        } catch (Exception var9) {
            log.error("hkeys error.", var9);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return retList;
    }

    /**
     * @Description:  zset set值
     * @param: key
     * @param: score 排名
     * @param: value 唯一
     * @return: boolean
     * @Author: chenping
     * @Date: 2020/9/17 17:20
     */
    public boolean zsetAdd(String key, long score, String value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.zadd(key, (double)score, value);
            boolean var6 = true;
            return var6;
        } catch (Exception var10) {
            log.error("setSortedSet error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    /**
     * @Description:  获取排序set集合
     * @param: key
     * @param: startScore
     * @param: endScore
     * @param: orderByDesc 降序
     * @return: java.util.Set<java.lang.String>
     * @Author: chenping
     * @Date: 2021/3/4 17:18
     */
    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
        Jedis jedis = null;

        Set var8;
        try {
            jedis = this.jedisPool.getResource();
            if (orderByDesc) {
                var8 = jedis.zrevrangeByScore(key, (double)endScore, (double)startScore);
                return var8;
            }

            var8 = jedis.zrangeByScore(key, (double)startScore, (double)endScore);
        } catch (Exception var12) {
            log.error("getSoredSet error.", var12);
            this.returnBrokenResource(jedis);
            return null;
        } finally {
            this.returnResource(jedis);
        }

        return var8;
    }

    public Set<String> zrangebyscore(String key, String max, String min) {
        Jedis jedis = null;
        Set var8;
        try {
            jedis = this.jedisPool.getResource();
            var8 = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            log.error("zrangebyscore error.", e);
            this.returnBrokenResource(jedis);
            return null;
        } finally {
            this.returnResource(jedis);
        }
        return var8;
    }

    public Set<Tuple> zrangeWithScores(String key,long start, long end) {
        Jedis jedis = null;
        Set<Tuple> retList = null;

        try {
            jedis = this.jedisPool.getResource();
            retList = jedis.zrangeWithScores(key,start,end);
        } catch (Exception var8) {
            log.error("zkeys error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return retList;
    }

    /**
     * @Description:  获取zset的所有的score和value
     * @param: key
     * @return: java.util.List<com.example.cp.entity.Tuples>
     * @Author: chenping
     * @Date: 2020/9/17 17:56
     */
    public List<Tuples> zsetGetAll(String key) {
        Jedis jedis = null;
        Set<Tuple> setList = null;
        List<Tuples> list = new ArrayList<>();

        try {
            jedis = this.jedisPool.getResource();
            setList = jedis.zrangeWithScores(key,0,-1);
            for (Tuple tuple : setList) {
                Tuples tuples = new Tuples();
                tuples.setElement(tuple.getElement());
                tuples.setScore(tuple.getScore());

                list.add(tuples);
            }
        } catch (Exception var8) {
            log.error("zkeys error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }
        return list;
    }

    public long countSoredSet(String key, long startScore, long endScore) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Long count = jedis.zcount(key, (double)startScore, (double)endScore);
            long var8 = count == null ? 0L : count;
            return var8;
        } catch (Exception var13) {
            log.error("countSoredSet error.", var13);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return 0L;
    }

    public boolean zsetDelete(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            long count = jedis.zrem(key, new String[]{value});
            boolean var6 = count > 0L;
            return var6;
        } catch (Exception var10) {
            log.error("delSortedSet error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Set var6;
            if (orderByDesc) {
                var6 = jedis.zrevrange(key, (long)startRange, (long)endRange);
                return var6;
            }

            var6 = jedis.zrange(key, (long)startRange, (long)endRange);
            return var6;
        } catch (Exception var10) {
            log.error("getSoredSetByRange error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }

    /**
     * @Description:  getScore
     * @param: key
     * @param: member
     * @param: index redis 库编号
     * @return: java.lang.Double
     * @Author: chenping
     * @Date: 2020/5/24
     */
    public Double getScore(String key, String member) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Double var4 = jedis.zscore(key, member);
            return var4;
        } catch (Exception var8) {
            log.error("getSoredSet error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return null;
    }


    public boolean set(String key, String value, int second) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.setex(key, second, value);
            boolean var5 = true;
            return var5;
        } catch (Exception var9) {
            log.error("set error.", var9);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    public boolean set(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.set(key, value);
            boolean var4 = true;
            return var4;
        } catch (Exception var8) {
            log.error("set error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    public String get(String key, String defaultValue) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            String var4 = StringUtils.isEmpty(jedis.get(key))? defaultValue:jedis.get(key);
            return var4;
        } catch (Exception var8) {
            log.error("get error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return defaultValue;
    }

    public boolean del(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.del(key);
            boolean var3 = true;
            return var3;
        } catch (Exception var7) {
            log.error("del error.", var7);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    } 
    /** 
     * @Description:  批量删除key
     * @param: keys 
     * @return: boolean 
     * @Author: chenping
     * @Date: 2021/3/4 16:58
     */
    public boolean del(Set<String> keys) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            for (String k : keys) {
                jedis.del(k);
            }
            return true;
        } catch (Exception var7) {
            log.error("del error.", var7);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    /**
     * @Description:  是否存在key
     * @param: key
     * @return: boolean
     * @Author: chenping
     * @Date: 2021/6/9 17:01
     */
    public boolean hasKey(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();

            return jedis.exists(key);
        } catch (Exception var7) {
            log.error("del error.", var7);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    /**
     * @Description:  将 key 中储存的数字值增一
     * @param: key
     * @return: long
     * @Author: chenping
     * @Date: 2020/9/17 17:13
     */
    public long incr(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            long var3 = jedis.incr(key);
            return var3;
        } catch (Exception var8) {
            log.error("incr error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return 0L;
    }

    public long decr(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            long var3 = jedis.decr(key);
            return var3;
        } catch (Exception var8) {
            log.error("decr error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return 0L;
    }

    public static String serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                return JSON.toJSONString(object);
            } catch (Exception var2) {
                log.error("serialize error.", var2);
                return null;
            }
        }
    }

    public static <T> T unserialize(String value, Class<T> clazz) {
        if (value == null) {
            return null;
        } else {
            try {
                return JSON.parseObject(value, clazz);
            } catch (Exception var3) {
                log.error("unserialize error.", var3);
                return null;
            }
        }
    }

    public boolean lock(String key) {
        Jedis jedis = null;

        boolean var4;
        try {
            jedis = this.jedisPool.getResource();
            Long result = jedis.setnx(key, "1");
            var4 = result == 1L;
        } catch (Exception var8) {
            log.error("lock-setnx error:", var8);
            this.returnBrokenResource(jedis);
            throw var8;
//            throw new Exception("获取锁出现异常：");
        } finally {
            this.returnResource(jedis);
        }

        return var4;
    }

    public boolean set(String key, String value, String nxxx, int second) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.set(key, value, nxxx, "seconds", second);
            boolean var6 = true;
            return var6;
        } catch (Exception var10) {
            log.error("set error.", var10);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return false;
    }

    /**
     * @Description:  分布锁
     * @param: key
     * @param: expireSecond
     * @return: boolean
     * @Author: chenping
     * @Date: 2021/4/12 9:51
     */
    public boolean lock(String key, int expireSecond) {
        Jedis jedis = null;

        boolean var5;
        try {
            jedis = this.jedisPool.getResource();
            String result = jedis.set(key, "1", "NX", "EX", expireSecond);
            if (!StringUtils.isEmpty(result)) {
                if ("OK".compareTo(result) == 0) {
                    var5 = true;
                    return var5;
                }

                var5 = false;
                return var5;
            }

            var5 = false;
            return var5;
        } catch (Exception var9) {
            log.error("lock-setnx error:", var9);
            this.returnBrokenResource(jedis);
            var5 = false;
        } finally {
            this.returnResource(jedis);
        }

        return var5;
    }

    public boolean tryLock(String key, int expireSecond, int tryCount) {
        if (this.lock(key, expireSecond)) {
            return true;
        } else {
            for(int i = 0; i < tryCount; ++i) {
                int sleepMills = RandomUtils.nextInt(20, 200);

                try {
                    Thread.currentThread();
                    Thread.sleep((long)sleepMills);
                } catch (InterruptedException var7) {
                    log.error(String.valueOf(var7));
                }

                if (this.lock(key, expireSecond)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void unlock(String key) {
        this.del(key);
    }

    private void returnBrokenResource(Jedis Jedis) {
        try {
            this.jedisPool.returnBrokenResource(Jedis);
        } catch (Exception var3) {
            log.error("returnBrokenResource error.", var3);
        }

    }

    private void returnResource(Jedis Jedis) {
        try {
            this.jedisPool.returnResource(Jedis);
        } catch (Exception var3) {
            log.error("returnResource error.", var3);
        }

    }

    public boolean setObject(String key, Object value) {
        return this.set(key, JSON.toJSONString(value));
    }

    public boolean setObject(String key, Object value, int second) {
        return this.set(key, JSON.toJSONString(value), second);
    }

    /**
     * @Description:  获取对象
     * @param: key
     * @param: clazz
     * @return: T
     * @Author: chenping
     * @Date: 2021/3/8 13:55
     */
    public <T> T getObject(String key, Class<T> clazz) {
        try {
            String value = this.get(key, "");
            return org.springframework.util.StringUtils.isEmpty(value) ? null : JSON.parseObject(value, clazz);
        } catch (Exception var4) {
            log.error("get redis but json error : " + var4 + "\r\nkey:" + key);
            return null;
        }
    }

    /**
     * @Description:  获取string的list结果
     * @param: key
     * @param: clazz
     * @return: java.util.List<T>
     * @Author: chenping
     * @Date: 2021/3/8 14:11
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        try {
            String value = this.get(key, "");
            return StringUtils.isEmpty(value) ? null : JSON.parseArray(value, clazz);
        } catch (Exception var4) {
            log.error("get redis but json error : " + var4 + "\r\nkey:" + key);
            return null;
        }
    }

    public Set<String> getKeys(String parrten) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Set var4 = jedis.keys(parrten);
            return var4;
        } catch (Exception var8) {
            log.error("get error.", var8);
            this.returnBrokenResource(jedis);
        } finally {
            this.returnResource(jedis);
        }

        return Collections.EMPTY_SET;
    }
}
