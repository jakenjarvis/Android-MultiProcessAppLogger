package com.tojc.logging.android.provider;

import java.util.concurrent.Callable;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.internal.item.LogTable;
import com.tojc.logging.android.settings.ContentProviderSettings;

import android.content.ComponentName;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;

public class LoggerDatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private static ContentProviderSettings settings = null;

	public static void loadSettings(Context context, Class<?> provider)
	{
		settings = new ContentProviderSettings(context, new ComponentName(context, provider));
	}

	public static ContentProviderSettings getSettings()
	{
		return settings;
	}

	public LoggerDatabaseHelper(Context context)
	{
		super(context, settings.getDatabaseName(), null, settings.getDatabaseVersion());
	}

	@Override
	public void onCreate(SQLiteDatabase database, final ConnectionSource connectionSource)
	{
		try
		{
			this.callInRetryTransaction(connectionSource,
				new Callable<Void>()
				{
					@Override
					public Void call() throws Exception
					{
						//TableUtils.createTable(connectionSource, ItemInfo.class);
						TableUtils.createTableIfNotExists(connectionSource, LogTable.class);
						return null;
					}
				}, null, 5, 3);
		}
		catch(Exception e)
		{
			String errormsg = String.format("Logger.LoggerDatabaseHelper createTableIfNotExists error. reason=%s", e.toString());
			Log.w(MultiProcessAppLogger.TAG, errormsg);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, final ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		try
		{
			this.callInRetryTransaction(connectionSource,
				new Callable<Void>()
				{
					@Override
					public Void call() throws Exception
					{
						TableUtils.dropTable(connectionSource, LogTable.class, true);
						TableUtils.createTable(connectionSource, LogTable.class);
						return null;
					}
				}, null, 5, 3);
		}
		catch(Exception e)
		{
			String errormsg = String.format("Logger.LoggerDatabaseHelper dropTable,createTable error. reason=%s", e.toString());
			Log.w(MultiProcessAppLogger.TAG, errormsg);
		}
	}
	
	public interface CheckRetry<T>
	{
		public boolean check(T value);
	}

	public <T> T callInRetryTransaction(final ConnectionSource connectionSource, final Callable<T> callable, CheckRetry<T> checkretry, long retrysleeptime, int retrycount) throws Exception
	{
		T result = null;
		boolean retry = true;
		int retries = retrycount;
		do
		{
			try
			{
				// MEMO: throw database is locked. (Exclusion between processes)
				result = TransactionManager.callInTransaction(connectionSource, callable);
				if(checkretry != null)
				{
					retry = checkretry.check(result);
				}
				else
				{
					retry = false;
				}
			}
			catch(SQLiteDatabaseLockedException e)
			{
				Log.w(MultiProcessAppLogger.TAG, "database is locked -> rest retry: " + retries);
				Thread.sleep(retrysleeptime);
			}
			retries--;
		}
		while(retry && (retries >= 0));
		return result;
	}

	public <T, U, V> T callSynchronizedRetryBatchTasks(final Dao<U, V> dao, Callable<T> callable, CheckRetry<T> checkretry, long retrysleeptime, int retrycount) throws Exception
	{
		T result = null;
		boolean retry = true;
		int retries = retrycount;
		do
		{
			try
			{
				synchronized(dao)
				{
					// MEMO: throw database is locked. (Exclusion between processes)
					result = dao.callBatchTasks(callable);
					if(checkretry != null)
					{
						retry = checkretry.check(result);
					}
					else
					{
						retry = false;
					}
				}
			}
			catch(SQLiteDatabaseLockedException e)
			{
				Log.w(MultiProcessAppLogger.TAG, "database is locked -> rest retry: " + retries);
				Thread.sleep(retrysleeptime);
			}
			retries--;
		}
		while(retry && (retries >= 0));

		return result;
	}
}
