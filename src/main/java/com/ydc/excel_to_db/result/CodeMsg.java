package com.ydc.excel_to_db.result;
/**
 * @Description: 封装需要返回的异常信息及自定义信息
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
public class CodeMsg {

    private int code;
    private String msg;
    public static CodeMsg FilE_ERROR = new CodeMsg(501, "上传文件类型异常");
    public static CodeMsg Excel_FORMAT_ERROR = new CodeMsg(502, "上传Excel表格格式有误<br>或者<br> 上传Excel数据为空");
    public static CodeMsg SERVER_ERROR = new CodeMsg(503, "服务端异常，请您联系管理员查看服务器日志");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @Description: 返回用户自定义的消息(非异常信息)
     * @Param: [msg]
     * @Retrun: com.ydc.excel_to_db.result.CodeMsg
     */
    public static CodeMsg userDefined(String msg) {
        return new CodeMsg(0, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}
