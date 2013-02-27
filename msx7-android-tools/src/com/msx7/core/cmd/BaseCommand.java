/**   
 * @Title: BaseCommand.java 
 * @Package com.yhiker.playmate.core.cmds 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-23 下午4:31:45 
 * @version V1.0   
 */
package com.msx7.core.cmd;


/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-7-23 下午4:31:45 类说明
 * 
 */
public abstract class BaseCommand implements ICommand {

	protected Request request;
	protected Response response;
	protected IResponseListener listener;

	protected Observable observable = new Observable();

	public void registerObserver(Observer observer) {
		if (observer == null) return;
		observable.addObserver(observer);
	}

	public Observable getObservable() {
		return observable;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public IResponseListener getListener() {
		return listener;
	}

	public void setListener(IResponseListener listener) {
		this.listener = listener;
	}
}
