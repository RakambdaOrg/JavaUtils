package fr.mrcraftcod.utils.base;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log
{
	private static final ArrayList<LogListener> listeners = new ArrayList<>();
	private static Logger logger;
	
	public interface LogListener
	{
		void onLogMessage(Level level, String message);
		
		void onLogMessage(Level level, String message, Throwable throwable);
	}
	
	public static void addListener(LogListener listener)
	{
		listeners.add(listener);
	}
	
	public static void warning(String format, Object... args)
	{
		warning(String.format(format, args));
	}
	
	public static void warning(String s)
	{
		log(Level.WARNING, s);
	}
	
	public static void log(Level level, String s)
	{
		getLogger().log(level, s);
		listeners.forEach(logListener -> logListener.onLogMessage(level, s));
	}
	
	public static Logger getLogger()
	{
		return logger != null ? logger : setAppName("MCCUtils");
	}
	
	public static Logger setAppName(String name)
	{
		logger = Logger.getLogger(name);
		
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.INFO);
		ch.setFormatter(new LoggerFormatter());
		logger.addHandler(ch);
		
		return logger;
	}
	
	public static void warning(Throwable e, String format, Object... args)
	{
		warning(e, String.format(format, args));
	}
	
	public static void warning(Throwable e, String s)
	{
		log(Level.WARNING, s, e);
	}
	
	public static void log(Level level, String s, Throwable e)
	{
		getLogger().log(level, s, e);
		listeners.forEach(logListener -> logListener.onLogMessage(level, s, e));
	}
	
	public static void info(String format, Object... args)
	{
		info(String.format(format, args));
	}
	
	public static void info(String s)
	{
		log(Level.INFO, s);
	}
	
	public static void error(String s)
	{
		log(Level.SEVERE, s);
	}
	
	public static void error(Throwable e, String format, Object... args)
	{
		error(e, String.format(format, args));
	}
	
	public static void error(Throwable e, String s)
	{
		log(Level.SEVERE, s, e);
	}
}
