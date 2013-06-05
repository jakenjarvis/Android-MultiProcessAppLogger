package com.tojc.logging.android.settings;

import com.tojc.logging.android.provider.Contract;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.net.Uri;

public class ContentProviderSettings
{
	private String databaseName = "MultiProcessAppLoggerDatabase";
	private int databaseVersion = 1;
	private String databaseTableName = Contract.LogTable.TABLENAME;

	private String authority = "";
	private String contentUriPath = "";
	private String mimetypeType = "";
	private String mimetypeName = "";

	private Uri contentUri = null;

	public ContentProviderSettings()
	{
	}

	public ContentProviderSettings(Context context, ComponentName componentName)
	{
		this.loadAndroidManifest(context, componentName);
	}

	public ContentProviderSettings loadAndroidManifest(Context context, ComponentName componentName)
	{
		ProviderInfo info = null;
		try
		{
			info = context.getPackageManager().getProviderInfo(componentName, PackageManager.GET_META_DATA);
		}
		catch(NameNotFoundException e)
		{
			e.printStackTrace();
			info = null;
		}

		if(info != null)
		{
			if(info.metaData.containsKey("database_name"))
			{
				this.databaseName = info.metaData.getString("database_name");
			}
			if(info.metaData.containsKey("database_version"))
			{
				this.databaseVersion = info.metaData.getInt("database_version");
			}
			//this.databaseTableName

			this.authority = info.authority;
			this.contentUriPath = this.databaseTableName;
			this.mimetypeType = this.databaseTableName;
			this.mimetypeName = this.authority + ".provider";

			this.makeContentUri();
		}
		return this;
	}

	public String getDatabaseName()
	{
		return this.databaseName;
	}
	public ContentProviderSettings setDatabaseName(String databaseName)
	{
		this.databaseName = databaseName;
		return this;
	}

	public int getDatabaseVersion()
	{
		return this.databaseVersion;
	}
	public ContentProviderSettings setDatabaseVersion(int databaseVersion)
	{
		this.databaseVersion = databaseVersion;
		return this;
	}

	public String getDatabaseTableName()
	{
		return this.databaseTableName;
	}
	public ContentProviderSettings setDatabaseTableName(String databaseTableName)
	{
		this.databaseTableName = databaseTableName;
		return this;
	}


	public String getAuthority()
	{
		return this.authority;
	}
	public ContentProviderSettings setAuthority(String authority)
	{
		this.authority = authority;
		return this;
	}

	public String getContentUriPath()
	{
		return this.contentUriPath;
	}
	public ContentProviderSettings setContentUriPath(String contentUriPath)
	{
		this.contentUriPath = contentUriPath;
		return this;
	}

	public String getMimetypeType()
	{
		return this.mimetypeType;
	}
	public ContentProviderSettings setMimetypeType(String mimetypeType)
	{
		this.mimetypeType = mimetypeType;
		return this;
	}

	public String getMimetypeName()
	{
		return this.mimetypeName;
	}
	public ContentProviderSettings setMimetypeName(String mimetypeName)
	{
		this.mimetypeName = mimetypeName;
		return this;
	}

	public Uri getContentUri()
	{
		return this.contentUri;
	}
	public ContentProviderSettings setContentUri(Uri contentUri)
	{
		this.contentUri = contentUri;
		return this;
	}
	public ContentProviderSettings makeContentUri()
	{
		this.contentUri = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(this.authority)
            .appendPath(this.contentUriPath)
            .build();
		return this;
	}
}
