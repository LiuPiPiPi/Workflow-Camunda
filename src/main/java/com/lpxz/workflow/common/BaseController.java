package com.lpxz.workflow.common;

import cn.hutool.core.date.DateUtil;
import com.lpxz.workflow.common.domain.TableDataInfo;
import com.lpxz.workflow.util.Resp;
import com.lpxz.workflow.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * @author LPxz
 * @date 2023/6/4
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    /**
     * 响应返回结果
     */
    protected Resp toResp(int rows) {
        return rows > 0 ? success() : error();
    }

    protected Resp toResp(int rows, String errMsg) {
        return rows > 0 ? success() : error(errMsg);
    }

    protected Resp toResp(boolean result) {
        return result ? success() : error();
    }

    protected Resp toResp(boolean result, String errMsg) {
        return result ? success() : error(errMsg);
    }

    /**
     * 返回成功
     */
    public Resp success() {
        return Resp.success();
    }

    public Resp success(Object obj) {
        return Resp.success(obj);
    }

    public Resp success(String message) {
        return Resp.success(message);
    }

    public Resp success(Object data, String message) {
        return Resp.success(data, message);
    }

    /**
     * 返回失败
     */
    public Resp error() {
        return Resp.error();
    }

    public Resp error(String message) {
        return Resp.error(message);
    }

    public Resp error(int code, String message) {
        return Resp.error(code, message);
    }
}
