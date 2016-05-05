package fr.mrcraftcod.utils;

import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
public class FileUtils
{
	public static File getAppDataFolder()
	{;
		return new File(getHomeFolder(),"AppData\\Roaming\\");
	}

	public static void createDirectories(File file)
	{
		if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
	}

	public static File getDesktopFolder()
	{
		return new File(getHomeFolder(), "Desktop");
	}

	public static File getHomeFolder()
	{
		return new File(System.getProperty("user.home"));
	}

	public static String sanitizeFileName(String name)
	{
		return name.chars().mapToObj(i -> (char) i).filter(c -> Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == ' ' || c == '.').map(String::valueOf).collect(Collectors.joining());
	}

	public static boolean forceDelete(File file)
	{
		try
		{
			org.apache.commons.io.FileUtils.forceDelete(file);
			return true;
		}
		catch(IOException e){}
		return false;
	}

	public static File askDirectory()
	{
		return askDirectory(null);
	}

	public static File askDirectory(File defaultFile)
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open output folder");
		if(defaultFile != null)
			directoryChooser.setInitialDirectory(defaultFile);
		return directoryChooser.showDialog(null);
	}
}
