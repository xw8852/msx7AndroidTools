/**   
 * @Title: Observer.java 
 * @Package com.hiker.onebyone.download 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-24 涓嬪崍3:08:12 
 * @version V1.0   
 */
package com.msx7.core.cmd;

/**
 * 
 * @author xiaowei
 * 
 *         Observer观察者接口<br/>
 *         他只接收ObserverInfo对象<br/>
 *         可以自定义类，继承自observerInfo对象
 */
public interface Observer {
	void update(Observable observable, ObserverInfo info);
}
