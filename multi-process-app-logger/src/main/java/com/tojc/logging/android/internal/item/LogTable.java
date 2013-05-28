package com.tojc.logging.android.internal.item;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.logging.android.provider.Contract;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultSortOrder;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.SortOrder;

@DatabaseTable(tableName=Contract.LogTable.TABLENAME)
public class LogTable
{
	@DatabaseField(columnName = Contract.LogTable._ID, generatedId = true)
	@DefaultSortOrder(weight=2, order=SortOrder.ASC)
	private int id;

	@DatabaseField
	private int level = 0;

	@DatabaseField
	private String fullClassName = "";

	@DatabaseField
	private String methodName = "";

	@DatabaseField
	@DefaultSortOrder(weight=1, order=SortOrder.ASC)
	private long startTimeValue = 0;

	@DatabaseField
	private int pid = 0;

	@DatabaseField
	private int tid = 0;

	@DatabaseField
	private String tag = "";

	@DatabaseField
	private String message = "";

	public LogTable()
	{
	}

	protected int getId()
	{
		return this.id;
	}
	protected LogTable setId(int id)
	{
		this.id = id;
		return this;
	}

	protected int getLevel()
	{
		return this.level;
	}
	protected LogTable setLevel(int level)
	{
		this.level = level;
		return this;
	}

	protected String getFullClassName()
	{
		return this.fullClassName;
	}
	protected LogTable setFullClassName(String fullClassName)
	{
		this.fullClassName = fullClassName;
		return this;
	}

	protected String getMethodName()
	{
		return this.methodName;
	}
	protected LogTable setMethodName(String methodName)
	{
		this.methodName = methodName;
		return this;
	}

	protected long getStartTimeValue()
	{
		return this.startTimeValue;
	}
	protected LogTable setStartTimeValue(long startTimeValue)
	{
		this.startTimeValue = startTimeValue;
		return this;
	}

	protected int getPid()
	{
		return this.pid;
	}
	protected LogTable setPid(int pid)
	{
		this.pid = pid;
		return this;
	}

	protected int getTid()
	{
		return this.tid;
	}
	protected LogTable setTid(int tid)
	{
		this.tid = tid;
		return this;
	}

	protected String getTag()
	{
		return this.tag;
	}
	protected LogTable setTag(String tag)
	{
		this.tag = tag;
		return this;
	}

	protected String getMessage()
	{
		return this.message;
	}
	protected LogTable setMessage(String message)
	{
		this.message = message;
		return this;
	}
}
