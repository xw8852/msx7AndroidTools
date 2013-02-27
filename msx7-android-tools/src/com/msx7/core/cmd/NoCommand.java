/**   
 * @Title: NoCommand.java 
 * @Package com.yhiker.playmate.core.cmds 
 * @Description: TODO
 * @author xiaowei   
 * @date 2012-8-1 下午3:08:52 
 * @version V1.0   
 */
package com.msx7.core.cmd;

/**
 * 
 * @author 作者 xiaowei
 * @创建时间 2012-8-1 下午3:08:52 类说明
 * 
 */
public final class NoCommand implements ICommand {

	@Override
	public void execute() {

	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public void onTerminal() {
	}

}
