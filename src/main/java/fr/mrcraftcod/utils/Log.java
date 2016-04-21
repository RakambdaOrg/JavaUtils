package fr.mrcraftcod.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
public class Log
{
	private static Logger logger;

	public static void info(String s)
	{
		info(true, s);
	}

	private static Logger getLogger()
	{
		return logger != null ? logger : setAppName("MCCUtils");
	}

	public static Logger setAppName(String name)
	{
		logger = Logger.getLogger(name);
		return logger;
	}

	public static void warning(String s, Throwable e)
	{
		warning(true, s, e);
	}

	public static void warning(boolean log, String s, Throwable e)
	{
		if(log)
			warning(s, e);
	}

	public static void info(boolean log, String s)
	{
		if(log)
			getLogger().log(Level.INFO, s);
	}

	public static void error(String s, Throwable e)
	{
		error(true, s, e);
	}

	public static void error(boolean log, String s, Throwable e)
	{
		if(log)
			getLogger().log(Level.SEVERE, s, e);
	}
}
