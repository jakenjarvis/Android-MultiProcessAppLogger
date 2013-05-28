package com.tojc.logging.android;

import java.util.List;

import com.tojc.logging.android.internal.Interface.LoggerModelPublicInterface;
import com.tojc.logging.android.internal.Interface.LoggerSettingsPublicInterface;
import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.item.LoggerItem;

public class Logger
{
	private Logger()
	{
	}

	public static LoggerSettingsPublicInterface settings()
	{
		return MultiProcessAppLogger.settings();
	}
	
	public static LoggerModelPublicInterface verbose()
	{
		return MultiProcessAppLogger.verbose();
	}
	
	public static LoggerModelPublicInterface debug()
	{
		return MultiProcessAppLogger.debug();
	}

	public static LoggerModelPublicInterface info()
	{
		return MultiProcessAppLogger.info();
	}

	public static LoggerModelPublicInterface warn()
	{
		return MultiProcessAppLogger.warn();
	}

	public static LoggerModelPublicInterface error()
	{
		return MultiProcessAppLogger.error();
	}

	public static String output()
	{
		return MultiProcessAppLogger.output();
	}

	public static List<LoggerItem> getLogCacheLists()
	{
		return MultiProcessAppLogger.getLogCacheLists();
	}

	public static List<String> getLogCacheStringLists()
	{
		return MultiProcessAppLogger.getLogCacheStringLists();
	}

	public static void clear()
	{
		MultiProcessAppLogger.clear();
	}
}
