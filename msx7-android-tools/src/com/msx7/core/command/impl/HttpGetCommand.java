package com.msx7.core.command.impl;

import java.net.URI;

import com.msx7.core.command.AbstractHttpCommand;


public class HttpGetCommand extends AbstractHttpCommand
{
	public HttpGetCommand(URI uri)
	{
		setURI(uri);
	}

	
	protected byte[] getBody()
	{
		return null;
	}

	
	protected String getContentType()
	{
		return null;
	}
}
