package com.msx7.core.command.impl;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;

import android.util.Log;

import com.msx7.core.command.AbstractHttpCommand;

public abstract class HttpPostCommand extends AbstractHttpCommand
{

	protected HttpPostCommand(URI uri)
	{
		setURI(uri);
	}

	
	protected void onBeforeExecute(HttpRequestBase request)
	{
		byte[] body = getBody();
		assert body != null && body.length > 0 : "Body must be present in HttpPostCommand. Use HttpGetCommand instead.";
		// request.addHeader("Content-Length", String.valueOf(body.length));

		Log.d("HttpPostCommand", "Body: " + body);

		HttpPost post = (HttpPost) request;

		ByteArrayEntity bodyData = new ByteArrayEntity(body);
		bodyData.setContentType(getContentType());
		post.setEntity(bodyData);
	}

	
	protected Object getErrorResponse(HttpResponse response)
	{
		return null;
	}

	
	protected Object getErrorResponse(Exception error)
	{
		return null;
	}
}
