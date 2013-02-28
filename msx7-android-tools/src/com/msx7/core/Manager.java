package com.msx7.core;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.text.TextUtils;
import android.util.Base64;

import com.msx7.core.cmd.BaseHttpCommand;
import com.msx7.core.cmd.ICommand;
import com.msx7.core.cmd.model.IParams;

public class Manager {
	private static final Manager instance = new Manager();
	private HashMap<String, Class<? extends ICommand>> maps=new HashMap<String, Class<? extends ICommand>>();
	private ExecutorService mPools;

	private Manager() {
		mPools = Executors.newFixedThreadPool(Controller.getApplication().max_progress);
	}

	public static final Manager getInstance() {
		return instance;
	}

	protected void registerCommand(int cmdId, String className) {
		if(TextUtils.isEmpty(className))return;
		 Class<?> _cls=Class.forName(className);
//		 if(_cls.isInterface()||_cl)return;
//		maps.put(Integer.toString(cmdId), Class.forName(className));
	}

	protected void registerCommand(int cmdId, Class<? extends ICommand> cls) {

	}

	public void execute(int cmdId, IParams params) {
		
	}
	
	private class ThreadRunable implements Runnable{
		
		
		@Override
		public void run() {
			
		}
		
	}

}
