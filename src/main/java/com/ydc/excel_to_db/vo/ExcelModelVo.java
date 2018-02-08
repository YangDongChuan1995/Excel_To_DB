package com.ydc.excel_to_db.vo;
import lombok.Data;

/**
 * @Description: 值对象
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Data
public class ExcelModelVo {
    // 格式校验成功的数据大小
    private long succSize;
    // 格式校验失败的数据大小
    private long failSize;
    // 导入数据库失败的数据大小
    private long failToDBSize;
    // 导入数据库成功的数据大小；
    private long succToDBSize;


    public ExcelModelVo(long succSize, long failSize, long failToDBSize) {
        this.succSize = succSize;
        this.failSize = failSize;
        this.failToDBSize = failToDBSize;
        // 校验成功的数据大小 - 导入数据库失败的数据大小 =成功导入的数据大小
        this.succToDBSize = succSize - failToDBSize;
    }
}
