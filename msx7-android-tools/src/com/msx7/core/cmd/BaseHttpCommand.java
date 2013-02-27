/**   
 * @Title: AbstractCommand.java 
 * @Package com.yhiker.playmate.core.cmds 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-23 下午4:33:33 
 * @version V1.0   
 */
package com.msx7.core.cmd;

import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-7-23 下午4:33:33 类说明
 * 
 */
public abstract class BaseHttpCommand extends BaseCommand {

	protected HttpUriRequest request;

	protected HttpResponse response;

	public HttpUriRequest getHttpRequest() {
		return request;
	}

	public void setHttpRequest(HttpUriRequest request) {
		this.request = request;
	}

	public HttpResponse getHttpResponse() {
		return response;
	}

	public void setHttpResponse(HttpResponse response) {
		this.response = response;
	}

	@Override
	public void execute() {
		try {
			// TODO： 通知Observers命令执行
			notifyStart();

			prepare();
			addHeader();
			go();
			if (getResponse() == null)
				setResponse(new Response());
			getResponse().tag = getRequest().tag;
			onAfterExecute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (getListener() != null) {
				if (getResponse() == null) {
					setResponse(new Response());
					getResponse().isError = true;
					getResponse().errorMsg = "数据异常";
				}
				if (!getResponse().isError) {
					getListener().onSuccess(getResponse());
				} else {
					getListener().onError(getResponse());
				}
			}
		}
		// TODO： 通知Observers命令执行结束
		notifyStop();
	}

	public void prepare() {
	}

	public void onAfterExecute() {
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public void onTerminal() {
	}

	public void addHeader() {

	}

	public abstract Response getSuccesData(HttpResponse response)
			throws Exception;

	public void go() {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
//			client.getParams().setParameter(
//					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
//			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//					TIME_OUT);
//			if (!NetUtil.checkNet()) {
//				Response data = new Response();
//				data.isError = true;
//				data.errorMsg = "网络连接异常";
//				setResponse(data);
//				return;
//			}
			response = client.execute(getHttpRequest());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				setResponse(getSuccesData(response));
			} else {
				Response data = new Response();
				data.isError = true;
				data.errorMsg = "服务器异常";
				Log.i(getClass().getSimpleName(), "HTTP ERROR CODE:"
						+ response.getStatusLine().getStatusCode());
				setResponse(data);
			}
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			if (getResponse() == null) {
				setResponse(new Response());
			}
			Response responseData = getResponse();
			responseData.isError = true;
			responseData.errorMsg = "连接服务器超时！";
			setResponse(responseData);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			if (getResponse() == null) {
				setResponse(new Response());
			}
			Response responseData = getResponse();
			responseData.isError = true;
			responseData.errorMsg = "连接服务器超时！";
			setResponse(responseData);
		} catch (java.net.ConnectException e) {
			e.printStackTrace();
			if (getResponse() == null) {
				setResponse(new Response());
			}
			Response responseData = getResponse();
			responseData.isError = true;
			responseData.errorMsg = "无法连接到服务器！";
			setResponse(responseData);
		} catch (Exception e) {
			e.printStackTrace();
			if (getResponse() == null) {
				setResponse(new Response());
			}
			Response responseData = getResponse();
			responseData.isError = true;
			responseData.errorMsg = "连接服务器失败，请检查网络连接！";
			setResponse(responseData);
		}

	}
	public void notifyStart() {
		ObserverInfo info = new ObserverInfo();
		info.status = CommandStatus.start;
		getObservable().notifyObservers(info);
	}

	public void notifyStop() {
		ObserverInfo info = new ObserverInfo();
		info.status = CommandStatus.finish;
		getObservable().notifyObservers(info);
	}
	
}
