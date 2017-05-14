package jp.whisper.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DevLog
{
	private final static Log	log	= LogFactory.getLog(DevLog.class);


	private DevLog()
	{
		// nothing
	}


	public static void trace(Object o)
	{
		if (log.isTraceEnabled())
		{
			log.trace(o);
		}
	}


	public static void trace(Object o, Throwable t)
	{
		if (log.isTraceEnabled())
		{
			log.trace(o, t);
		}
	}


	public static void debug(Object o)
	{
		if (log.isDebugEnabled())
		{
			log.debug(o);
		}
	}


	public static void debug(Object o, Throwable t)
	{
		if (log.isDebugEnabled())
		{
			log.debug(o, t);
		}
	}


	public static void info(Object o)
	{
		if (log.isInfoEnabled())
		{
			log.info(o);
		}
	}


	public static void info(Object o, Throwable t)
	{
		if (log.isInfoEnabled())
		{
			log.info(o, t);
		}
	}


	public static void warn(Object o)
	{
		if (log.isWarnEnabled())
		{
			log.warn(o);
		}
	}


	public static void warn(Object o, Throwable t)
	{
		if (log.isWarnEnabled())
		{
			log.warn(o, t);
		}
	}


	public static void error(Object o)
	{
		if (log.isErrorEnabled())
		{
			log.error(o);
		}
	}


	public static void error(Object o, Throwable t)
	{
		if (log.isErrorEnabled())
		{
			log.error(o, t);
		}
	}


	public static void fatal(Object o)
	{
		if (log.isFatalEnabled())
		{
			log.fatal(o);
		}
	}


	public static void fatal(Object o, Throwable t)
	{
		if (log.isFatalEnabled())
		{
			log.fatal(o, t);
		}
	}

}


