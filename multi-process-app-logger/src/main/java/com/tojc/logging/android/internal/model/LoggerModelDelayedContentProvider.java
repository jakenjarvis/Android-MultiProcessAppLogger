package com.tojc.logging.android.internal.model;

import java.util.ArrayList;
import java.util.List;

import com.tojc.logging.android.internal.item.LoggerItem;
import com.tojc.logging.android.settings.LoggerSettings;

import android.content.ContentValues;
import android.content.Context;

public class LoggerModelDelayedContentProvider extends LoggerModelContentProvider implements Runnable
{
	private volatile Thread thread = null;
	private List<ContentValues> queue = null;
	
	public LoggerModelDelayedContentProvider()
	{
		super();
		this.thread = null;
		this.queue = new ArrayList<ContentValues>();
	}

	@Override
	public void initialize(Context context, LoggerSettings settings)
	{
		super.initialize(context, settings);
	}

	@Override
	public void addLogCache(LoggerItem value)
	{
		this.enqueueContentValues(createContentValues(value));
		this.setLastTimeValue(value.getStartTimeValue());

		if(this.thread == null)
		{
			this.thread = new Thread(this);
			this.thread.start();
		}
	}

	private synchronized void enqueueContentValues(ContentValues value)
	{
		this.queue.add(value);
	}

	private synchronized List<ContentValues> dequeueContentValues()
	{
		List<ContentValues> result = new ArrayList<ContentValues>();
		for(ContentValues value : this.queue)
		{
			result.add(value);
		}
		this.queue.clear();
		return result;
	}

	private int execute()
	{
		int result = 0;
		List<ContentValues> values = this.dequeueContentValues();
		result = values.size();
		if(result >= 1)
		{
	        this.context.getContentResolver().bulkInsert(
	        		settings.getContentProviderSettings().getContentUri(),
	        		values.toArray(new ContentValues[result])
	        		);
	    }
		return result;
	}

	@Override
	public void run()
	{
		while(this.thread != null)
		{
			int result = this.execute();
			if(result >= 1)
			{
		        try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
				}
		    }
			else
			{
				this.thread = null;
			}
		}
	}

	@Override
	public String output()
	{
		this.execute();
		return super.output();
	}

	@Override
	public List<LoggerItem> getLogCacheLists()
	{
		this.execute();
		return super.getLogCacheLists();
	}

	@Override
	public List<String> getLogCacheStringLists()
	{
		this.execute();
		return super.getLogCacheStringLists();
	}
}
