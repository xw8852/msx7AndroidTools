package com.msx7.test;

import com.google.gson.Gson;
import com.msx7.core.command.model.IParams;

public class GuoliParam implements IParams {
	public String action;
	public String platformType = "android";
	public Object param;
	
	public GuoliParam(String action, Object param) {
		super();
		this.action = action;
		this.param = param;
	}

	@Override
	public String toParams() {
		return new Gson().toJson(this);
	}

}
