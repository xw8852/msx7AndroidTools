package com.msx7.core.command.impl;

import com.msx7.core.command.AbstractBaseCommand;
import com.msx7.core.command.ICommand;
import com.msx7.core.command.model.Response;




public class IdentityCommand extends AbstractBaseCommand implements ICommand {
	
	public void execute() {
		Response response = new Response();
		response.setError(false);
		setResponse(response);
		notifyListener(true);
	}

}
