package com.tojc.logging.android;

import java.util.List;

import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.Interface.LoggerSettingsPublicInterface;
import com.tojc.logging.android.internal.item.LoggerItem;

public class Log
{
	private Log()
	{
	}

	public static LoggerSettingsPublicInterface settings()
	{
		return MultiProcessAppLogger.settings();
	}


	public static void v(String tag, String msg)
	{
		((LoggerItem)MultiProcessAppLogger.verbose()).print(tag, msg, null, null);
	}

	public static void v(String tag, String msg, Throwable tr)
	{
		((LoggerItem)MultiProcessAppLogger.verbose()).print(tag, msg, tr, null);
	}

	public static void d(String tag, String msg)
	{
		((LoggerItem)MultiProcessAppLogger.debug()).print(tag, msg, null, null);
	}

	public static void d(String tag, String msg, Throwable tr)
	{
		((LoggerItem)MultiProcessAppLogger.debug()).print(tag, msg, tr, null);
	}

	public static void i(String tag, String msg)
	{
		((LoggerItem)MultiProcessAppLogger.info()).print(tag, msg, null, null);
	}

	public static void i(String tag, String msg, Throwable tr)
	{
		((LoggerItem)MultiProcessAppLogger.info()).print(tag, msg, tr, null);
	}

	public static void w(String tag, String msg)
	{
		((LoggerItem)MultiProcessAppLogger.warn()).print(tag, msg, null, null);
	}

	public static void w(String tag, String msg, Throwable tr)
	{
		((LoggerItem)MultiProcessAppLogger.warn()).print(tag, msg, tr, null);
	}

//	public static void w(String tag, Throwable tr)
//	{
//		((LoggerItem)MultiProcessAppLogger.warn()).print(tag, null, tr, null);
//	}

	public static void e(String tag, String msg)
	{
		((LoggerItem)MultiProcessAppLogger.error()).print(tag, msg, null, null);
	}

	public static void e(String tag, String msg, Throwable tr)
	{
		((LoggerItem)MultiProcessAppLogger.error()).print(tag, msg, tr, null);
	}


	public static void v(String msg)
	{
		MultiProcessAppLogger.verbose().print(msg);
	}

	public static void v(String msg, Throwable tr)
	{
		MultiProcessAppLogger.verbose().print(msg, tr);
	}

	public static void d(String msg)
	{
		MultiProcessAppLogger.debug().print(msg);
	}

	public static void d(String msg, Throwable tr)
	{
		MultiProcessAppLogger.debug().print(msg, tr);
	}

	public static void i(String msg)
	{
		MultiProcessAppLogger.info().print(msg);
	}

	public static void i(String msg, Throwable tr)
	{
		MultiProcessAppLogger.info().print(msg, tr);
	}

	public static void w(String msg)
	{
		MultiProcessAppLogger.warn().print(msg);
	}

	public static void w(String msg, Throwable tr)
	{
		MultiProcessAppLogger.warn().print(msg, tr);
	}

	public static void e(String msg)
	{
		MultiProcessAppLogger.error().print(msg);
	}

	public static void e(String msg, Throwable tr)
	{
		MultiProcessAppLogger.error().print(msg, tr);
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
