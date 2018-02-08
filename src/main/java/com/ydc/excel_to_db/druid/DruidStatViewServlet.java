package com.ydc.excel_to_db.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid/*",
        initParams = {
//                @WebInitParam(name="allow",value="0.0.0.0"),// IP白名单 (没有配置或者为空，则允许所有访问)
//                @WebInitParam(name="deny",value="192.168.0.0"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "admin"),// druid监控页面登陆用户名
                @WebInitParam(name = "loginPassword", value = "admin"),// druid监控页面登陆密码
                @WebInitParam(name = "resetEnable", value = "true")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 1L;
}