package com.tojc.logging.android.internal.model;

import com.tojc.logging.android.internal.Interface.LoggerModel;

public enum LoggerModelType
{
	OriginalModel("OriginalModel", null),
	MemoryCache("MemoryCache", new LoggerModelMemoryCache()),
	ContentProvider("ContentProvider", new LoggerModelContentProvider()),
	DelayedContentProvider("DelayedContentProvider", new LoggerModelDelayedContentProvider());

	private LoggerModelType(String name, LoggerModel model)
	{
		this.name = name;
		this.model = model;
	}

	private final String name;
	private final LoggerModel model;

	public String getName()
	{
		return this.name;
	}

	public LoggerModel getInstance()
	{
		return this.model;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
