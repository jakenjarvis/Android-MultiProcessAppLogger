package com.tojc.logging.android.internal.item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tojc.logging.android.internal.LoggerLevel;
import com.tojc.logging.android.internal.MultiProcessAppLogger;

public class LogTableItem extends LogTable
{
	// not database value
	private DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	private long lastTimeValue = 0;
	private long maxLogTime = MultiProcessAppLogger.DEFAULT_MAX_LOG_TIME;

	public LogTableItem()
	{
	}

	public long getMaxLogTime()
	{
		return this.maxLogTime;
	}
	public LogTableItem setMaxLogTime(long maxLogTime)
	{
		this.maxLogTime = maxLogTime;
		return this;
	}

	@Override
	public int getId()
	{
		return super.getId();
	}
	@Override
	public LogTableItem setId(int id)
	{
		return (LogTableItem)super.setId(id);
	}

	@Override
	public int getLevel()
	{
		return super.getLevel();
	}
	@Override
	public LogTableItem setLevel(int level)
	{
		return (LogTableItem)super.setLevel(level);
	}
	public LoggerLevel getLoggerLevel()
	{
		return LoggerLevel.toLoggerLevel(this.getLevel());
	}
	public LogTableItem setLoggerLevel(LoggerLevel level)
	{
		return this.setLevel(level.getCode());
	}

	@Override
	public String getFullClassName()
	{
		return super.getFullClassName();
	}
	@Override
	public LogTableItem setFullClassName(String fullClassName)
	{
		return (LogTableItem)super.setFullClassName(fullClassName);
	}
	public String getClassName()
	{
		return this.getFullClassName().substring(this.getFullClassName().lastIndexOf(".") + 1);
	}

	@Override
	public String getMethodName()
	{
		return super.getMethodName();
	}
	@Override
	public LogTableItem setMethodName(String methodName)
	{
		return (LogTableItem)super.setMethodName(methodName);
	}

	@Override
	public long getStartTimeValue()
	{
		return super.getStartTimeValue();
	}
	@Override
	public LogTableItem setStartTimeValue(long startTimeValue)
	{
		return (LogTableItem)super.setStartTimeValue(startTimeValue);
	}
    public Date getStartTimeDate()
    {
    	return new Date(this.getStartTimeValue());
    }
    public String getStartTimeDateFormat()
    {
    	return this.dateformat.format(getStartTimeDate());
    }

	public long getLastTimeValue()
	{
		return this.lastTimeValue;
	}
	public LogTableItem setLastTimeValue(long lastTimeValue)
	{
		this.lastTimeValue = lastTimeValue;
		return this;
	}
    public Date getLastTimeDate()
    {
    	return new Date(this.lastTimeValue);
    }
    public String getLastTimeDateFormat()
    {
    	return this.dateformat.format(getLastTimeDate());
    }

    public long getDifferenceTimeValue()
    {
    	return this.getStartTimeValue() - this.lastTimeValue;
    }
    public String getDifferenceTimeMessage()
    {
		String result = "";
		long logtime = getDifferenceTimeValue();
		if(logtime >= this.maxLogTime)
		{
			result = "ovr5min";
		}
		else
		{
			result = String.format("%07d", logtime);
		}
		return result;
    }

	@Override
	public int getPid()
	{
		return super.getPid();
	}
	@Override
	public LogTableItem setPid(int pid)
	{
		return (LogTableItem)super.setPid(pid);
	}

	@Override
	public int getTid()
	{
		return super.getTid();
	}
	@Override
	public LogTableItem setTid(int tid)
	{
		return (LogTableItem)super.setTid(tid);
	}

	@Override
	public String getTag()
	{
		return super.getTag();
	}
	@Override
	public LogTableItem setTag(String tag)
	{
		return (LogTableItem)super.setTag(tag);
	}

	@Override
	public String getMessage()
	{
		return super.getMessage();
	}
	@Override
	public LogTableItem setMessage(String message)
	{
		return (LogTableItem)super.setMessage(message);
	}

	public String makeKeyString()
	{
		String result = String.format("%010d %1s %s",
				this.getId(),
				this.getLoggerLevel().toString(),
				this.getStartTimeDateFormat());
		return result;
	}

	private String makeValueString()
	{
		StringBuilder result = new StringBuilder();
		String space = " ";

		result.append(this.getTag());
		result.append(space);
		result.append(makeHeaderString());
		result.append(space);
		result.append(this.getMessage());

		return result.toString();
	}

	public String makeLineString()
	{
		StringBuilder result = new StringBuilder();
		result.append(makeKeyString());
		result.append(" : ");
		result.append(makeValueString());
		result.append("\n");
		return result.toString();
	}

	public String makeHeaderString()
	{
		StringBuilder header = new StringBuilder();
		header.append("[");
		header.append(this.getDifferenceTimeMessage());
		header.append(" ");
		header.append(MultiProcessAppLogger.format("PID=%06d,TID=%06d", this.getPid(), this.getTid()));
		header.append("] ");
		header.append(this.getClassName());
		header.append(".");
		header.append(this.getMethodName());
		header.append(":");
		return header.toString();
	}

	public String makeBodyString()
	{
		StringBuilder result = new StringBuilder();
		String space = " ";
		result.append(makeHeaderString());
		result.append(space);
		result.append(this.getMessage());
		return result.toString();
	}

	@Override
	public String toString()
	{
		return makeLineString();
	}
}
