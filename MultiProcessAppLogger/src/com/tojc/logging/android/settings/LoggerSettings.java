package com.tojc.logging.android.settings;

import android.content.ComponentName;
import android.content.Context;

import com.tojc.logging.android.internal.Interface.LoggerModel;
import com.tojc.logging.android.internal.Interface.LoggerSettingsPublicInterface;
import com.tojc.logging.android.internal.model.LoggerModelType;
import com.tojc.logging.android.provider.LoggerProvider;

public class LoggerSettings implements LoggerSettingsPublicInterface
{
	private Context context;
    private boolean logEnabled;
	private String packageName;
	private LoggerModelType loggerModelType;

    private ContentProviderSettings contentProviderSettings;

    public LoggerSettings()
    {
    	this.context = null;
    	this.logEnabled = true;
    	this.packageName = "";
    	this.loggerModelType = LoggerModelType.DelayedContentProvider;
    	this.contentProviderSettings = null;
    }

	@Override
	public void initialize(Context context)
	{
		this.initialize(context, LoggerModelType.DelayedContentProvider);
	}

	@Override
	public void initialize(Context context, LoggerModelType loggerModelType)
	{
		if(this.context == null)
		{
			this.context = context;
	    	this.contentProviderSettings = new ContentProviderSettings(context, new ComponentName(context, LoggerProvider.class));
		}
		this.setLoggerModelType(loggerModelType);

		for(LoggerModelType type : LoggerModelType.values())
		{
    		type.getInstance().initialize(this.context, this);
		}
	}

    public Context getContext()
	{
		return this.context;
	}

    public LoggerModel getLoggerModel()
	{
		return this.loggerModelType.getInstance();
	}

    @Override
	public LoggerModelType getLoggerModelType()
	{
		return this.loggerModelType;
	}
    @Override
	public void setLoggerModelType(LoggerModelType loggerModelType)
	{
    	this.loggerModelType = loggerModelType;
	}

	@Override
	public boolean isLogEnabled()
	{
		return this.logEnabled;
	}
	@Override
	public void setLogEnabled(boolean logEnabled)
	{
		this.logEnabled = logEnabled;
	}

	public String getPackageName()
	{
		return this.packageName;
	}
	@Override
    public void setPackageName(String packageName)
	{
    	this.packageName = packageName;
	}

	@Override
	public ContentProviderSettings getContentProviderSettings()
	{
		return this.contentProviderSettings;
	}
}
