package fr.mrcraftcod.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
public class Log
{
	private static Logger logger;

	public static void info(String s)
	{
		getLogger().log(Level.INFO, s);
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
		getLogger().log(Level.WARNING, s, e);
	}

	public static void warning(boolean log, String s, Throwable e)
	{
		if(log)
			warning(s, e);
	}

	public static void info(boolean log, String s)
	{
		if(log)
			info(s);
	}
}
