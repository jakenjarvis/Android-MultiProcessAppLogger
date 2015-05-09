package com.tojc.logging.android.internal.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.item.LoggerItem;

public class LoggerModelMemoryCache extends LoggerModelBase
{
	private static class Counter
	{
		private int index = 0;

		public Counter()
		{
			this.index = 0;
		}

	    public int getIndex()
	    {
	    	return this.index;
	    }
	    public synchronized void increment()
	    {
	    	this.index = (this.index >= Integer.MAX_VALUE) ? 0 : this.index + 1;
	    }
	}

	// Log cache
	@SuppressWarnings("serial")
	private Map<Integer, LoggerItem> cache = new LinkedHashMap<Integer, LoggerItem>(MultiProcessAppLogger.DEFAULT_CACHE_SIZE)
	{
		@Override
        protected boolean removeEldestEntry(Map.Entry<Integer,LoggerItem> eldest)
        {
            return size() > MultiProcessAppLogger.DEFAULT_CACHE_SIZE;
        }
	};

	private Counter counter = new Counter();

	public LoggerModelMemoryCache()
	{
		super();
		this.cache.clear();
		this.setLastTimeValue(System.currentTimeMillis());
	}

	@Override
	public void addLogCache(LoggerItem value)
	{
		this.counter.increment();
		synchronized(value)
		{
			int index = this.counter.getIndex();
			value.setId(index);
		}
		this.cache.put(value.getId(), value);

		this.setLastTimeValue(value.getStartTimeValue());
	}

	@Override
	public String output()
	{
		StringBuilder result = new StringBuilder();
		for(Map.Entry<Integer,LoggerItem> e : this.cache.entrySet())
		{
			result.append(e.getValue().makeLineString());
		}
		return result.toString();
	}

	@Override
	public List<LoggerItem> getLogCacheLists()
	{
		return (List<LoggerItem>)this.cache.values();
	}

	@Override
	public List<String> getLogCacheStringLists()
	{
		List<String> result = new LinkedList<String>();
		for(Map.Entry<Integer,LoggerItem> e : this.cache.entrySet())
		{
			result.add(e.getValue().makeLineString());
		}
		return result;
	}

	@Override
	public void clear()
	{
		this.cache.clear();
	}
}
