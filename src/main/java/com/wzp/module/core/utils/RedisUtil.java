package com.wzp.module.core.utils;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings("all")
public class RedisUtil {

    private static RedisUtil RedisUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        RedisUtil = this;
    }

    /**
     * 指定缓存失效时间
     */
    public static boolean expire(String key, long seconds) {
        return RedisUtil.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间,返回0表示永久有效
     */
    public static long getExpire(String key) {
        return RedisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        return RedisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 删除key
     */
    public static void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                RedisUtil.redisTemplate.delete(key[0]);
            } else {
                RedisUtil.redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取
     */
    public static Object get(String key) {
        return key == null ? null : RedisUtil.redisTemplate.opsForValue().get(key);
    }

    /**
     * 放入
     */
    public static void put(String key, Object value) {
        RedisUtil.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * seconds 小于0表示永不失效,单位秒
     */
    public static void put(String key, Object value, long seconds) {
        if (seconds > 0) {
            RedisUtil.redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
        } else {
            put(key, value);
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return RedisUtil.redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return RedisUtil.redisTemplate.opsForValue().increment(key, -delta);
    }

    /* -------------------------------Map----------------------------------- */

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hashGet(String key, String item) {
        return RedisUtil.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hashGetAll(String key) {
        return RedisUtil.redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashPut
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static void hashPutAll(String key, Map<String, Object> map) {
        RedisUtil.redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param seconds 时间(秒)
     * @return true成功 false失败
     */
    public static void hashPutAll(String key, Map<String, Object> map, long seconds) {
        RedisUtil.redisTemplate.opsForHash().putAll(key, map);
        if (seconds > 0) {
            expire(key, seconds);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hashPut(String key, String item, Object value) {
        RedisUtil.redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hashDelete(String key, Object... item) {
        RedisUtil.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hashHasKey(String key, String item) {
        return RedisUtil.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hashIncr(String key, String item, double by) {
        return RedisUtil.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hashDecr(String key, String item, double by) {
        return RedisUtil.redisTemplate.opsForHash().increment(key, item, -by);
    }

    /* -------------------------------Set----------------------------------- */

    public static Set<Object> setGet(String key) {
        return RedisUtil.redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean setHasKey(String key, Object value) {
        return RedisUtil.redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long setPut(String key, Object... values) {
        return RedisUtil.redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param seconds   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long setPutAndTime(String key, long seconds, Object... values) {
        Long count = RedisUtil.redisTemplate.opsForSet().add(key, values);
        if (seconds > 0)
            expire(key, seconds);
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long getSetSize(String key) {
        return RedisUtil.redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        Long count = RedisUtil.redisTemplate.opsForSet().remove(key, values);
        return count;
    }

    /* -------------------------------List----------------------------------- */

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public static List<Object> listGet(String key, long start, long end) {
        return RedisUtil.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long listGetSize(String key) {
        return RedisUtil.redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object listGetIndex(String key, long index) {
        return RedisUtil.redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param seconds  时间(秒)
     * @return
     */
    public static void listPut(String key, Object value) {
        RedisUtil.redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param seconds  时间(秒)
     * @return
     */
    public static void listPut(String key, Object value, long seconds) {
        RedisUtil.redisTemplate.opsForList().rightPush(key, value);
        if (seconds > 0) expire(key, seconds);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param seconds  时间(秒)
     * @return
     */
    public static void listPut(String key, List<Object> value) {
        RedisUtil.redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param seconds  时间(秒)
     * @return
     */
    public static void listPut(String key, List<Object> value, long seconds) {
        RedisUtil.redisTemplate.opsForList().rightPushAll(key, value);
        if (seconds > 0) expire(key, seconds);
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static void listUpdateIndex(String key, long index, Object value) {
        RedisUtil.redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long listRemove(String key, long count, Object value) {
        return RedisUtil.redisTemplate.opsForList().remove(key, count, value);
    }

}
