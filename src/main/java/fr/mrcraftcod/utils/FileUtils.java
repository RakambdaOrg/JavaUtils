package fr.mrcraftcod.utils;

import java.io.File;
import java.util.stream.Collectors;
public class FileUtils
{
	public static File getAppDataFolder()
	{;
		return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\");
	}

	public static void createDirectories(File file)
	{
		if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
	}

	public static File getDesktopFolder()
	{
		return new File(System.getProperty("user.home"), "Desktop");
	}

	public static String sanitizeFileName(String name)
	{
		return name.chars().mapToObj(i -> (char) i).filter(c -> Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == ' ').map(String::valueOf).collect(Collectors.joining());
	}
}
