package com.msx7.core.command;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

public final class ErrorCode {
	/**
	 * HTTP STATUS ERROR
	 */
	public static final int ERROR_HTTP_STATUS = 0x0001;
	/**
	 * 连接超时跑出的异常
	 * <ul>
	 * <li>
	 * {@link ConnectTimeoutException}</li>
	 * <li>
	 * {@link SocketTimeoutException}</li>
	 * </ul>
	 */
	public static final int ERROR_TIME_OUT = ERROR_HTTP_STATUS << ERROR_HTTP_STATUS;
	/**
	 * 没有连接到网络
	 */
	public static final int ERROR_NET = ERROR_TIME_OUT << ERROR_HTTP_STATUS;
	/**
	 * 无法连接到服务器
	 * <ul>
	 * <li> {@link ConnectException}</li>
	 * </ul>
	 */
	public static final int ERROR_CONNECT_SERVER = ERROR_NET << ERROR_HTTP_STATUS;
	/**
	 * 读取网络文件时，文件不存在
	 */
	public static final int ERROR_NOT_FOUND_FILE_NET = ERROR_CONNECT_SERVER << ERROR_HTTP_STATUS;
	/**
	 * 未知网络请求异常
	 */
	public static final int ERROR_NET_UNKOWN = ERROR_NOT_FOUND_FILE_NET << ERROR_HTTP_STATUS;

}
