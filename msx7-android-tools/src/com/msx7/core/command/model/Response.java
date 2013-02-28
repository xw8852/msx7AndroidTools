/**   
 * @Title: Response.java 
 * @Package com.yhiker.playmate.core.common 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-23 下午4:10:28 
 * @version V1.0   
 */
package com.msx7.core.command.model;

/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-7-23 下午4:10:28 类说明
 * 
 */
public class Response {

	public Object result;
	public boolean error;

	

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    public Object getData() {
        return result;
    }

    public void setData(Object data) {
        this.result = data;
    }
}
