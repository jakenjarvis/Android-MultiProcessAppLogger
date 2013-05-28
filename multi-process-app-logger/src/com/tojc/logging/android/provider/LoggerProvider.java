package com.tojc.logging.android.provider;

import com.tojc.logging.android.internal.item.LogTable;
import com.tojc.logging.android.settings.ContentProviderSettings;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd.SubType;

public class LoggerProvider extends OrmLiteSimpleContentProvider<LoggerDatabaseHelper>
{
    @Override
    protected Class<LoggerDatabaseHelper> getHelperClass()
    {
        return LoggerDatabaseHelper.class;
    }

    @Override
    public boolean onCreate()
    {
    	// create instance... load ContentProviderSettings
    	@SuppressWarnings("unused")
		LoggerDatabaseHelper helper = this.getHelper();

    	ContentProviderSettings settings = LoggerDatabaseHelper.getSettings();

    	this.setMatcherController(new MatcherController()
			.add(LogTable.class)
			.setDefaultContentUri(
					settings.getAuthority(),
					settings.getContentUriPath())
			.setDefaultContentMimeTypeVnd(
					settings.getMimetypeName(),
					settings.getMimetypeType())
			.add(SubType.DIRECTORY, "", Contract.LogTable.CONTENT_URI_PATTERN_MANY)
			.add(SubType.ITEM, "#", Contract.LogTable.CONTENT_URI_PATTERN_ONE)
			);

        return true;
    }
}
