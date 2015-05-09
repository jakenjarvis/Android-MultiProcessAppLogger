package com.tojc.logging.android.internal;

import java.util.List;

import com.tojc.logging.android.internal.item.LoggerItem;
import com.tojc.logging.android.internal.model.LoggerModelType;
import com.tojc.logging.android.settings.ContentProviderSettings;
import com.tojc.logging.android.settings.LoggerSettings;

import android.content.Context;

public interface Interface
{
	public interface LoggerSettingsPublicInterface
	{
		public void initialize(Context context, Class<?> provider);
		public void initialize(Context context, Class<?> provider, LoggerModelType loggerModelType);
		public void initialize(Context context, Class<?> provider, LoggerModel loggerModel);

		public LoggerModelType getLoggerModelType();
		public void setLoggerModelType(LoggerModelType loggerModelType);
		public LoggerModel getLoggerModel();
		public void setLoggerModel(LoggerModel loggerModel);

		public boolean isLoggerEnabled();
		public void setLoggerEnabled(boolean loggerEnabled);
		public boolean isLogcatEnabled();
		public void setLogcatEnabled(boolean logcatEnabled);

		public void setPackageName(String packageName);

	    public ContentProviderSettings getContentProviderSettings();
	}

	public interface LoggerModelPublicInterface
	{
		public void print(String msg);
		public void print(String msg, Throwable tr);
		public void print(String format, Object... args);
		public void print(Throwable tr, String format, Object... args);
		public void print_start();
		public void print_end();
	}

	public interface LoggerModel
	{
		public void initialize(Context context, LoggerSettings settings);
		public LoggerSettings getSettings();

		public LoggerItem createInstance(LoggerLevel level);

		public String getPackageName();
		public long getLastTimeValue();

		public void addLogCache(LoggerItem value);
		public String output();

		public List<LoggerItem> getLogCacheLists();
		public List<String> getLogCacheStringLists();
		public void clear();
	}
}
