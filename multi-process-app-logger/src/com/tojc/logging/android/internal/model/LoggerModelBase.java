package com.tojc.logging.android.internal.model;

import com.tojc.logging.android.internal.LoggerLevel;
import com.tojc.logging.android.internal.Interface.LoggerModel;
import com.tojc.logging.android.internal.item.LoggerItem;
import com.tojc.logging.android.internal.MultiProcessAppLogger;
import com.tojc.logging.android.settings.LoggerSettings;

import android.content.Context;
import android.util.Log;

public abstract class LoggerModelBase implements LoggerModel
{
	private static final String[] LIST_IGNORE_PACKAGES = {
		"com\\.tojc\\.logging\\.android\\.internal\\.",
		"dalvik\\.",
		"java\\.",
		"android\\.",
		"com\\.android\\.",
		"com\\.google\\."
		};

	private static final String[] LIST_FUNCTIONS = {
		"onCreate","onReceive",
		"onStartCommand","onBind",
		"onRestart","onStart","onResume","onPause","onStop",
		"onUnbind","onDestroy"
		};

	protected LoggerSettings settings = null;
	protected Context context;
	protected String packageName = "";
	protected long lastTimeValue = 0;

	public LoggerModelBase()
	{
	}

	@Override
	public void initialize(Context context, LoggerSettings settings)
	{
		this.context = context;
		this.settings = settings;
		this.packageName = this.settings.getPackageName();
		this.lastTimeValue = 0;

		if((this.packageName == null) || (this.packageName.length() <= 0))
		{
			StringBuilder packages = new StringBuilder();
			String pksepa = "";
			for(String item : LIST_IGNORE_PACKAGES)
			{
				packages.append(pksepa);
				packages.append(item);
				pksepa = "|";
			}

			StringBuilder functions = new StringBuilder();
			String fcsepa = "";
			for(String item : LIST_FUNCTIONS)
			{
				functions.append(fcsepa);
				functions.append(item);
				fcsepa = "|";
			}

	    	StringBuilder regex = new StringBuilder();
	    	regex.append("^(?!(");
	    	regex.append(packages.toString());
	    	regex.append("))");
	    	regex.append(".*[.](");
	    	regex.append(functions.toString());
	    	regex.append(")[(].*");

			StackTraceElement targetelm = null;
			try
			{
				StackTraceElement[] trace = Thread.currentThread().getStackTrace();
				for(StackTraceElement item : trace)
				{
					String name = item.toString();
					if(name.matches(regex.toString()))
					{
						targetelm = item;
						break;
					}
				}
				if(targetelm == null)
				{
					targetelm = trace[4];
				}

				Class<?> target_class = Class.forName(targetelm.getClassName());
				String classname = target_class.getPackage().getName();
				String[] splitname = classname.split("\\.", -1);

				String classpackagename = "";
				if(splitname.length >= 2)
				{
					String prd = "";
					for(int i = 0; i <= splitname.length - 2; i++)
					{
						classpackagename += prd + splitname[i];
						prd = ".";
					}
				}
				else
				{
					classpackagename = classname; 
				}
				this.packageName = classpackagename;
				this.settings.setPackageName(this.packageName);
			}
			catch (Exception e)
			{
				Log.e(MultiProcessAppLogger.TAG, "create packageName error:", e);
			}
		}
	}

	@Override
	public LoggerSettings getSettings()
	{
		return this.settings;
	}

	@Override
	public LoggerItem createInstance(LoggerLevel level)
	{
		if(this.context == null)
		{
			throw new IllegalStateException("Not calling the .settings().initialize().");
		}
		return new LoggerItem(this, level);
	}

	@Override
	public long getLastTimeValue()
	{
		return this.lastTimeValue;
	}
	public void setLastTimeValue(long lastTimeValue)
	{
		this.lastTimeValue = lastTimeValue;
	}

	@Override
	public String getPackageName()
	{
		return this.packageName;
	}
}
