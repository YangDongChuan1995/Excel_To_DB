package com.ydc.excel_to_db.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Description: 为了方便扩展，这里的字段命名规则为col[列]+1[列数]
 * 例如：col1代表的就是第一列的数据，通过@Excel(name=?)注解进行区分
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Data
public class ExcelModel {
    // 官方文档 http://easypoi.mydoc.io/
    @Excel(name = "序号")
    @NotBlank(message = "该字段不能为空")
    @Max(value = 1000)
    private String col1;
    @Excel(name = "姓名")
    @Pattern(regexp = "[\\u4E00-\\u9FA5]{2,5}", message = "姓名中文2-5位")
    private String col2;
    @Excel(name = "性别")
    private String col3;
    @Excel(name = "出生年月", format = "yyyy.MM.dd")
    private Date col4;
    @Excel(name = "民族")
    private String col5;
    @Excel(name = "籍贯")
    private String col6;
    @Excel(name = "文化程度")
    private String col7;
    @Excel(name = "参工时间", format = "yyyy.MM")
    private Date col8;
    @Excel(name = "政治面貌")
    private String col9;
    @Excel(name = "职务")
    private String col10;
    @Excel(name = "现处室时间", format = "yyyy.MM")
    private Date col11;
    @Excel(name = "任现职位时间", format = "yyyy.MM")
    private Date col12;
    @Excel(name = "任现职级时间", format = "yyyy.MM")
    private Date col13;
    @Excel(name = "职称")
    private String col14;
    @Excel(name = "执业资格")
    private String col15;
    @Excel(name = "进局时间", format = "yyyy.MM")
    private Date col16;
    @Excel(name = "实务导师")
    private String col17;
    @Excel(name = "备注")
    private String col18;

    public String getCol2() {
        return col2;
    }
}
