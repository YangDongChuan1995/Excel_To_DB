package com.ydc.excel_to_db.service;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.ydc.excel_to_db.domain.ExcelModel;
import com.ydc.excel_to_db.handler.ExcelModelHandler;
import com.ydc.excel_to_db.result.CodeMsg;
import com.ydc.excel_to_db.vo.ExcelModelVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportService {
    /**
     * @Description: 1.校验上传的文件类型及其对应的数据
     * 2.将通过（1）的数据转换为实体对象集合
     * 3.将对象集合传入cacheAndPublish()中
     * 4.封装本次数据校验结果并返回
     * @Param: [file]
     * @Retrun: com.ydc.excel_to_db.result.CodeMsg
     */
    CodeMsg verfiyExcel(MultipartFile file);

    /**
     * @Description: 清空redis中的部分旧数据
     * @Param: []
     * @Retrun: void
     */
    void cleanCache();

    /**
     * @Description: 将参数result中的部分数据存入redis中，并把格式校验成功的数据发布至对应频道中
     * @Param: [result]
     * @Retrun: void
     */
    void cacheAndPublish(ExcelImportResult<ExcelModel> result);

    /**
     * @Description: 将实体对象存入数据库中
     * @Param: [excelModel]
     * @Retrun: boolean 保存成功，返回true；保存失败，返回false；
     */
    boolean save(ExcelModel excelModel);

    /**
     * @Description: 根据不同的失败类型的名称(failTypeName), 返回对应的数据
     * @Param: [failTypeName]
     * @Retrun: java.util.List<com.ydc.excel_to_db.domain.ExcelModel>
     */
    List<ExcelModel> getFailList(String failTypeName);

    /**
     * @Description: 根据key值，返回redis中对应的结果
     * @Param: [key]
     * @Retrun: long
     */
    long getTempSize(String key);

    /**
     * @Description: 获取同步结果页面中饼状图所需的数据
     * @Param: []
     * @Retrun: com.ydc.excel_to_db.vo.ExcelModelVo 封装的值对象
     */
    ExcelModelVo getResultData();
}
