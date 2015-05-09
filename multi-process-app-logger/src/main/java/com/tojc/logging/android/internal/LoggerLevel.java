package com.tojc.logging.android.internal;

import android.util.Log;

public enum LoggerLevel
{
	Verbose(Log.VERBOSE, "V"),
	Debug(Log.DEBUG, "D"),
	Info(Log.INFO, "I"),
	Warn(Log.WARN, "W"),
	Error(Log.ERROR, "E");

	private LoggerLevel(int code, String name)
	{
		this.code = code;
		this.name = name;
	}

	private final int code;
	private final String name;

	public int getCode()
	{
		return this.code;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public static LoggerLevel toLoggerLevel(int code)
	{
		LoggerLevel result = LoggerLevel.Error;
        for(LoggerLevel value : values())
        {
        	if(value.getCode() == code)
        	{
        		result = value;
        		break;
        	}
        }
        return result;
	}
}
