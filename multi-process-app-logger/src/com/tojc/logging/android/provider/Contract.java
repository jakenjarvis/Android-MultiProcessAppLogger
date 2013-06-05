package com.tojc.logging.android.provider;

import android.provider.BaseColumns;

public class Contract
{
	public static class LogTable implements BaseColumns
	{
		public static final String TABLENAME = "LogTable";

		// feild info
        public static final String LEVEL = "level";
		public static final String FULLCLASSNAME = "fullClassName";
		public static final String METHODNAME = "methodName";
		public static final String STARTTIMEVALUE = "startTimeValue";
		public static final String PID = "pid";
		public static final String TID = "tid";
		public static final String TAG = "tag";
		public static final String MESSAGE = "message";

        // content uri pattern code
        public static final int CONTENT_URI_PATTERN_MANY = 1;
        public static final int CONTENT_URI_PATTERN_ONE = 2;
	}
}
