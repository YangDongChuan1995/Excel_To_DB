package com.ydc.excel_to_db.redis;

import com.ydc.excel_to_db.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 通过StringRedisTemplate类对redis进行操作
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Repository
public class RedisDao {

    @Autowired
    private StringRedisTemplate template;

    /**
     * @Description: 操作redis中数据结构为String的数据，进行set操作
     * @Param: [key, value]
     * @Retrun: void
     */
    public <T> void setStringKey(String key, T value) {
        ValueOperations<String, String> ops = template.opsForValue();
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        ops.set(key, str);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行get操作,获取单个对象的json字符串
     * @Param: [key, clazz]
     * @Retrun: T
     */
    public <T> T getStringValue(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String str = ops.get(key);
        // 将json串转换成对应(clazz)的对象
        return JsonUtil.stringToBean(str, clazz);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行get操作,获取对象集合的json字符串
     * @Param: [key, clazz]
     * @Retrun: java.util.List<T>
     */
    public <T> List<T> getStringListValue(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String str = ops.get(key);
        // 将json串转换成对应(clazz)的对象集合
        return JsonUtil.stringToList(str, clazz);
    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行get操作,获取对应list中“所有”的数据
     * @Param: [key, clazz]
     * @Retrun: java.util.List<T>
     */
    public <T> List<T> getListValue(String key, Class<T> clazz) {
        ListOperations<String, String> ops = template.opsForList();
        // 获取对应list中的所有的数据
        List<String> list = ops.range(key, 0, -1);
        // 创建大小为对应list大小(ops.size(key)的ArrayList，避免后期进行扩容操作
        List<T> result = new ArrayList<T>(ops.size(key).intValue());
        // 遍历从redis中获取到的list，依次将其转换为对应(clazz)的对象并添加至结果集(result)中
        for (String s : list) {
            result.add(JsonUtil.stringToBean(s, clazz));
        }
        return result;
    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行push操作(这里默认从左left进行插入)
     * @Param: [key, value]
     * @Retrun: void
     */
    public <T> void leftPushKey(String key, T value) {
        ListOperations<String, String> ops = template.opsForList();
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        // 将转换后的json字符串存入redis
        ops.leftPush(key, str);

    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行pop操作(这里默认从右right进行取出)
     * @Param: [key, clazz]
     * @Retrun: T
     */
    public <T> T rightPopValue(String key, Class<T> clazz) {
        ListOperations<String, String> ops = template.opsForList();
        String str = ops.rightPop(key);
        return JsonUtil.stringToBean(str, clazz);
    }

    /**
     * @Description: 操作redis中数据结构为List的数据，进行size操作，获取对应的list的长度大小
     * @Param: [key]
     * @Retrun: java.lang.Long
     */
    public Long getListSize(String key) {
        ListOperations<String, String> ops = template.opsForList();
        return ops.size(key);
    }

    /**
     * @Description: 消息发布
     * @Param: [channelName, value] 频道名称
     * @Retrun: void
     */
    public <T> void publish(String channelName, T value) {
        // 将参数value转换为String类型
        String str = JsonUtil.beanToString(value);
        // 将消息(str)发布到指定的频道(channelName)
        template.convertAndSend(channelName, str);
    }

    /**
     * @Description: 操作redis中数据结构为String的数据，进行increment操作
     * @Param: [key, num]
     * @Retrun: java.lang.Long
     */
    public Long incrOrDecr(String key, long num) {
        ValueOperations<String, String> ops = template.opsForValue();
        return ops.increment(key, num);
    }

    /**
     * @Description: 清空参数keyList中的所有值(key)所对应的redis里的数据
     * @Param: [keyList]
     * @Retrun: void
     */
    public void cleanCache(List<String> keyList) {
        template.delete(keyList);
    }


}
