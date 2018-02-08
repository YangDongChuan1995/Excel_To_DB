package com.ydc.excel_to_db.redis;

import com.ydc.excel_to_db.domain.ExcelModel;
import com.ydc.excel_to_db.service.ImportService;
import com.ydc.excel_to_db.util.Constant;
import com.ydc.excel_to_db.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 消息接收者，将其在ExcelToDbApplication.java中注入消息监听容器(MessageListenerAdapter)中
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Service
public class Receiver {
    @Autowired
    ImportService importService;
    @Autowired
    RedisDao redisDao;
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    /**
     * @Description: 用于接收单个对象，将对象同步至数据库，如果同步失败，则存入redis中
     * @Param: [message] “fastjson”转换后的json字符串
     * @Retrun: void
     */
    public void receiveSingle(String message) throws InterruptedException {
        // 将json字符串转换成实体对象
        ExcelModel excelModel = JsonUtil.stringToBean(message, ExcelModel.class);
        // 尝试同步数据库并返回同步结果
        boolean result = importService.save(excelModel);
        if (!result)
            // 同步失败，将其存入redis中
            redisDao.leftPushKey(Constant.failToDBKey, excelModel);
        else
            // 同步成功，输出至日志中
            log.info("成功插入数据库的数据：" + excelModel.getCol2());
        // 加上-1，其实也就是做减1操作
        redisDao.incrOrDecr(Constant.succSizeTempKey, -1);

    }

    /**
     * @Description: 用于接收对象集合，将集合遍历拆分成单个对象并进行发布
     * @Param: [message] “fastjson”转换后的json字符串
     * @Retrun: void
     */
    public void receiveList(String message) throws InterruptedException {
        // 将json字符串转换成对象集合
        List<ExcelModel> list = JsonUtil.stringToList(message, ExcelModel.class);
        // 遍历集合,并依次将其发布
        for (ExcelModel excelModel : list) {
            redisDao.publish(Constant.receiveSingle, excelModel);
        }
    }
}
