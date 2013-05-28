package com.tojc.logging.android.internal.item;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

import com.tojc.logging.android.internal.LoggerLevel;
import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.Interface.LoggerModel;
import com.tojc.logging.android.internal.Interface.LoggerModelPublicInterface;

public class LoggerItem extends LogTableItem implements LoggerModelPublicInterface
{
	private LoggerModel model = null;
	
	public LoggerItem()
	{
	}

	public LoggerItem(LoggerModel model)
	{
		this.model = model;
	}

	public LoggerItem(LoggerModel model, LoggerLevel level)
	{
		StackTraceElement element = getStackTraceElement();
		
		this.model = model;
		this.setId(0)
			.setLoggerLevel(level)
			.setFullClassName(getClassNameFromElement(element))
			.setMethodName(getMethodNameFromElement(element))
			.setStartTimeValue(System.currentTimeMillis())
			.setLastTimeValue(this.model.getLastTimeValue())
			.setPid(android.os.Process.myPid())
			.setTid(android.os.Process.myTid())
			.setTag(this.model.getPackageName())
			.setMessage("");
	}

	public synchronized LoggerModel getModel()
	{
		return this.model;
	}
	public synchronized void setModel(LoggerModel model)
	{
		this.model = model;
	}

	private StackTraceElement getStackTraceElement()
	{
		StackTraceElement result = null;
		try
		{
			StackTraceElement[] trace = Thread.currentThread().getStackTrace();
			result = trace[7];
		}
		catch (Exception e)
		{
			String errormsg = String.format("Logger.setInfo stack error. reason=%s", e.getMessage());
			Log.e(MultiProcessAppLogger.TAG, errormsg);
			result = null;
		}
		return result;
	}

	private String getClassNameFromElement(StackTraceElement element)
	{
		String result = "";
		if(element != null)
		{
			result = element.getClassName();
		}
		return result;
	}

	private String getMethodNameFromElement(StackTraceElement element)
	{
		String result = "";
		if(element != null)
		{
			result = element.getMethodName();
		}
		return result;
	}

	protected void android_util_Log_print()
	{
		switch(this.getLoggerLevel())
		{
			case Verbose:
				Log.v(this.getTag(), this.makeBodyString());
				break;
			case Debug:
				Log.d(this.getTag(), this.makeBodyString());
				break;
			case Info:
				Log.i(this.getTag(), this.makeBodyString());
				break;
			case Warn:
				Log.w(this.getTag(), this.makeBodyString());
				break;
			case Error:
				Log.e(this.getTag(), this.makeBodyString());
				break;
		}
	}

	public synchronized void print(String tag, String msg, Throwable tr, String format, Object... args)
	{
		this.setMessage(makeMessage(msg, tr, format, args));
		this.setTag(tag);
		if(this.model.getSettings().isLogcatEnabled())
		{
			android_util_Log_print();
		}
		if(this.model.getSettings().isLoggerEnabled())
		{
			this.model.addLogCache(this);
		}
	}

	@Override
	public synchronized void print(String msg)
	{
		this.setMessage(makeMessage(msg, null, null));
		if(this.model.getSettings().isLogcatEnabled())
		{
			android_util_Log_print();
		}
		if(this.model.getSettings().isLoggerEnabled())
		{
			this.model.addLogCache(this);
		}
	}

	@Override
	public synchronized void print(String msg, Throwable tr)
	{
		this.setMessage(makeMessage(msg, tr, null));
		if(this.model.getSettings().isLogcatEnabled())
		{
			android_util_Log_print();
		}
		if(this.model.getSettings().isLoggerEnabled())
		{
			this.model.addLogCache(this);
		}
	}

	@Override
	public synchronized void print(String format, Object... args)
	{
		this.setMessage(makeMessage(null, null, format, args));
		if(this.model.getSettings().isLogcatEnabled())
		{
			android_util_Log_print();
		}
		if(this.model.getSettings().isLoggerEnabled())
		{
			this.model.addLogCache(this);
		}
	}

	@Override
	public synchronized void print(Throwable tr, String format, Object... args)
	{
		this.setMessage(makeMessage(null, tr, format, args));
		if(this.model.getSettings().isLogcatEnabled())
		{
			android_util_Log_print();
		}
		if(this.model.getSettings().isLoggerEnabled())
		{
			this.model.addLogCache(this);
		}
	}

	@Override
	public void print_start()
	{
		this.print("start");
	}

	@Override
	public void print_end()
	{
		this.print("end");
	}

	private static String makeMessage(String msg, Throwable tr, String format, Object... args)
	{
		StringBuilder result = new StringBuilder();
		String space = " ";

		if(msg != null)
		{
			result.append(space);
			result.append(msg);
		}
		
		if(format != null)
		{
			result.append(space);
			result.append(MultiProcessAppLogger.format(format, args));
		}

		if(tr != null)
		{
			result.append("\n");
			result.append(getStackTraceString(tr));
		}
		return result.toString().trim();
	}

	private static String getStackTraceString(Throwable tr)
	{
		String result = "";
		try
		{
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			tr.printStackTrace(printWriter);
			result = stringWriter.toString();
			stringWriter.close();
			
			Throwable cause = tr.getCause();
			if(cause != null)
			{
				result += "\n Caused by: \n";
				result += getStackTraceString(cause);
			}
		}
		catch(Exception e)
		{
			result = tr.toString();
			e.printStackTrace();
		}
		return result;
	}
}
