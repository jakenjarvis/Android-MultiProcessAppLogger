package com.tojc.logging.android.multiprocessapploggersample;

public class LoggerProvider extends com.tojc.logging.android.provider.LoggerProvider
{
    @Override
    protected Class<?> getLoggerProviderClass()
    {
        return com.tojc.logging.android.multiprocessapploggersample.LoggerProvider.class;
    }
}
