/**   
 * @Title: IResponseListener.java 
 * @Package com.yhiker.playmate.core.common 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-23 下午4:11:48 
 * @version V1.0   
 */
package com.msx7.core.cmd;

import com.msx7.core.command.model.Response;

/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-7-23 下午4:11:48 类说明
 * 
 */
public interface IResponseListener {
	public void onSuccess(Response response);

	public void onError(Response response);
}
