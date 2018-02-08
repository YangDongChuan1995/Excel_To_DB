package com.ydc.excel_to_db.result;
/**
 * @Description: 封装返回给前台的数据
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * @Description: 成功时候的调用
     * @Param: [data]
     * @Retrun: com.ydc.excel_to_db.result.Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * @Description: 失败时候的调用
     * @Param: [codeMsg]
     * @Retrun: com.ydc.excel_to_db.result.Result<T>
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {

        return new Result<T>(codeMsg);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
