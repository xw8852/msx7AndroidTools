package com.msx7.core;

import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.msx7.core.command.AbstractHttpCommand;
import com.msx7.core.command.ICommand;
import com.msx7.core.command.impl.HttpJsonPostCommand;
import com.msx7.core.command.model.Request;

public class Manager {
	
	public static final int CMD_JSON_POST=0x10001;
	private static final Manager instance = new Manager();
	private HashMap<String, Class<? extends ICommand>> maps = new HashMap<String, Class<? extends ICommand>>();
	private ExecutorService mPools;
	
	

	private Manager() {
		mPools = Executors
				.newFixedThreadPool(Controller.getApplication().max_progress);
		registerCommand(CMD_JSON_POST, HttpJsonPostCommand.class);
	}

	public static final Manager getInstance() {
		return instance;
	}

	protected void registerCommand(int cmdId, Class<? extends ICommand> cls) {
		if (cls == null)
			return;
		maps.put(Integer.toString(cmdId), cls);
	}

	public void execute(int cmdId, Request request) {
		Class<? extends ICommand> cmd_class = maps.get(Integer.toString(cmdId));
		if (cmd_class == null)
			return;
		int modifiers = cmd_class.getModifiers();
		if ((modifiers & Modifier.ABSTRACT) == 0)
			return;
		if ((modifiers & Modifier.INTERFACE) == 0)
			return;
		try {
			ICommand cmd = cmd_class.newInstance();
			cmd.setRequest(request);
			if (cmd instanceof AbstractHttpCommand) {
				((AbstractHttpCommand) cmd).setURI(URI.create(request.url));
			}
			mPools.submit(new ThreadCall(cmd));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	private class ThreadCall implements Callable<ICommand> {
		ICommand cmd;

		public ThreadCall(ICommand cmd) {
			super();
			this.cmd = cmd;
		}

		@Override
		public ICommand call() throws Exception {
			cmd.execute();
			return cmd;
		}

	}

}
