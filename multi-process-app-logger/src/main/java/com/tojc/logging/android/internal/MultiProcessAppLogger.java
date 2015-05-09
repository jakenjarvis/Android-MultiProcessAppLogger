package com.tojc.logging.android.internal;

import java.util.List;

import android.util.Log;

import com.tojc.logging.android.internal.Interface.LoggerModelPublicInterface;
import com.tojc.logging.android.internal.Interface.LoggerSettingsPublicInterface;
import com.tojc.logging.android.internal.item.LoggerItem;
import com.tojc.logging.android.settings.LoggerSettings;

public class MultiProcessAppLogger
{
	public static final String TAG = "MultiProcessAppLogger";

	public static final int DEFAULT_CACHE_SIZE = 500;
	public static final int DEFAULT_CACHE_SIZE2 = 2500;
	public static final long DEFAULT_MAX_LOG_TIME = 300000;

	private static final LoggerSettings settings = new LoggerSettings();

	private MultiProcessAppLogger()
	{
	}

	public static LoggerSettingsPublicInterface settings()
	{
		return settings;
	}
	
	public static LoggerModelPublicInterface verbose()
	{
		return settings.getLoggerModel().createInstance(LoggerLevel.Verbose);
	}
	
	public static LoggerModelPublicInterface debug()
	{
		return settings.getLoggerModel().createInstance(LoggerLevel.Debug);
	}

	public static LoggerModelPublicInterface info()
	{
		return settings.getLoggerModel().createInstance(LoggerLevel.Info);
	}

	public static LoggerModelPublicInterface warn()
	{
		return settings.getLoggerModel().createInstance(LoggerLevel.Warn);
	}

	public static LoggerModelPublicInterface error()
	{
		return settings.getLoggerModel().createInstance(LoggerLevel.Error);
	}

	public static String output()
	{
		return settings.getLoggerModel().output();
	}

	public static List<LoggerItem> getLogCacheLists()
	{
		return settings.getLoggerModel().getLogCacheLists();
	}

	public static List<String> getLogCacheStringLists()
	{
		return settings.getLoggerModel().getLogCacheStringLists();
	}

	public static void clear()
	{
		settings.getLoggerModel().clear();
	}

	public static String format(String format, Object... args)
	{
		String result = "";
		try
		{
			result = String.format(format == null ? "" : format, args);
		}
		catch(RuntimeException e)
		{
			result = String.format("format error. reason=%s, format=%s", e.getMessage(), format);
			Log.e(MultiProcessAppLogger.TAG, result);
		}
		return result;
	}
}
