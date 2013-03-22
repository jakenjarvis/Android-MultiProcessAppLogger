package com.tojc.logging.android.internal.model;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.item.LoggerItem;
import com.tojc.logging.android.provider.Contract;
import com.tojc.logging.android.settings.LoggerSettings;

public class LoggerModelContentProvider extends LoggerModelBase
{
	public LoggerModelContentProvider()
	{
		super();
	}

	@Override
	public void initialize(Context context, LoggerSettings settings)
	{
		super.initialize(context, settings);
		this.setLastTimeValue(queryGetLastTimeValue());
	}

	@Override
	public void addLogCache(LoggerItem value)
	{
		insertValue(value);
		this.setLastTimeValue(value.getStartTimeValue());
	}

	@Override
	public String output()
	{
		StringBuilder result = new StringBuilder();
		long lastTimeValue = 0;
		List<LoggerItem> loggerItemValues = queryLoggerItemValueList(MultiProcessAppLogger.DEFAULT_CACHE_SIZE);
		for(LoggerItem value : loggerItemValues)
		{
			value.setLastTimeValue(lastTimeValue);
			result.append(value.makeLineString());
			lastTimeValue = value.getStartTimeValue();
		}
		return result.toString();
	}

	@Override
	public List<LoggerItem> getLogCacheLists()
	{
		List<LoggerItem> result = queryLoggerItemValueList(MultiProcessAppLogger.DEFAULT_CACHE_SIZE2);
		long lastTimeValue = 0;
		for(LoggerItem value : result)
		{
			value.setLastTimeValue(lastTimeValue);
			lastTimeValue = value.getStartTimeValue();
		}
		return result;
	}

	@Override
	public List<String> getLogCacheStringLists()
	{
		List<String> result = new LinkedList<String>();
		long lastTimeValue = 0;
		List<LoggerItem> loggerItemValues = queryLoggerItemValueList(MultiProcessAppLogger.DEFAULT_CACHE_SIZE2);
		for(LoggerItem value : loggerItemValues)
		{
			value.setLastTimeValue(lastTimeValue);
			result.add(value.makeLineString());
			lastTimeValue = value.getStartTimeValue();
		}
		return result;
	}

	@Override
	public void clear()
	{
		deleteAll();
	}

	private void deleteAll()
	{
		try
		{
	        this.context.getContentResolver().delete(settings.getContentProviderSettings().getContentUri(), "", null);
		}
		catch (Exception e)
		{
			Log.e(MultiProcessAppLogger.TAG, "deleteAll error:", e);
		}
	}

	private long queryGetLastTimeValue()
	{
		long result = 0;
		
		Cursor cursor = null;
		try
		{
			cursor = this.context.getContentResolver()
				.query(settings.getContentProviderSettings().getContentUri(),
					new String[] {
						"MAX(" + Contract.LogTable.STARTTIMEVALUE + ")"
						+ " AS " + Contract.LogTable.STARTTIMEVALUE
					},
					null,
					null,
					null);
			if((cursor != null) && (cursor.moveToFirst()))
			{
				result = cursor.getLong(cursor.getColumnIndex(Contract.LogTable.STARTTIMEVALUE));
			}
		}
		catch (Exception e)
		{
			Log.e(MultiProcessAppLogger.TAG, "queryGetLastTimeValue error:", e);
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
				cursor = null;
			}
		}
		return result;
	}
	
	private void insertValue(LoggerItem value)
	{
		try
		{
			ContentValues values = createContentValues(value);
	        this.context.getContentResolver().insert(settings.getContentProviderSettings().getContentUri(), values);
		}
		catch (Exception e)
		{
			Log.e(MultiProcessAppLogger.TAG, "insertValue error:", e);
		}
	}

	protected ContentValues createContentValues(LoggerItem value)
	{
		ContentValues result = null;
		try
		{
			result = new ContentValues();
			result.clear();
			result.put(Contract.LogTable.LEVEL, value.getLevel());
	        result.put(Contract.LogTable.FULLCLASSNAME, value.getFullClassName());
	        result.put(Contract.LogTable.METHODNAME, value.getMethodName());
	        result.put(Contract.LogTable.STARTTIMEVALUE, value.getStartTimeValue());
	        result.put(Contract.LogTable.PID, value.getPid());
	        result.put(Contract.LogTable.TID, value.getTid());
	        result.put(Contract.LogTable.TAG, value.getTag());
	        result.put(Contract.LogTable.MESSAGE, value.getMessage());
		}
		catch (Exception e)
		{
			Log.e(MultiProcessAppLogger.TAG, "createContentValues error:", e);
		}
		return result;
	}

	private List<LoggerItem> queryLoggerItemValueList(int limit)
	{
		List<LoggerItem> result = new LinkedList<LoggerItem>();

		Cursor cursor = null;
		try
		{
			String whereLimit = "";
			if(limit != 0)
			{
				whereLimit = " LIMIT " + String.valueOf(limit);
			}

			cursor = this.context.getContentResolver()
				.query(settings.getContentProviderSettings().getContentUri(),
					null,
					null,
					null,
					Contract.LogTable.STARTTIMEVALUE + " DESC, "
					+ Contract.LogTable._ID + " DESC "
					+ whereLimit
					);
			if((cursor != null) && (cursor.moveToFirst()))
			{
				cursor.moveToLast();
				do
				{
					LoggerItem value = (LoggerItem)new LoggerItem()
						.setId(cursor.getInt(cursor.getColumnIndex(Contract.LogTable._ID)))
						.setLevel(cursor.getInt(cursor.getColumnIndex(Contract.LogTable.LEVEL)))
						.setFullClassName(cursor.getString(cursor.getColumnIndex(Contract.LogTable.FULLCLASSNAME)))
						.setMethodName(cursor.getString(cursor.getColumnIndex(Contract.LogTable.METHODNAME)))
						.setStartTimeValue(cursor.getLong(cursor.getColumnIndex(Contract.LogTable.STARTTIMEVALUE)))
						.setPid(cursor.getInt(cursor.getColumnIndex(Contract.LogTable.PID)))
						.setTid(cursor.getInt(cursor.getColumnIndex(Contract.LogTable.TID)))
						.setTag(cursor.getString(cursor.getColumnIndex(Contract.LogTable.TAG)))
						.setMessage(cursor.getString(cursor.getColumnIndex(Contract.LogTable.MESSAGE)));
					result.add(value);
				}
				while(cursor.moveToPrevious());
			}
		}
		catch (Exception e)
		{
			Log.e(MultiProcessAppLogger.TAG, "queryLoggerItemValueList error:", e);
		}
		finally
		{
			if(cursor != null)
			{
				cursor.close();
				cursor = null;
			}
		}
		return result;
	}
}
