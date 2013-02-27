/**   
 * @Title: ICommand.java 
 * @Package com.yhiker.playmate.cmds 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-7-23 下午4:06:46 
 * @version V1.0   
 */
package com.msx7.core.cmd;


/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-7-23 下午4:06:46 类说明
 * 
 */
public interface ICommand {
	public abstract void execute();
	public boolean isTerminal();
	public void onTerminal();
}
