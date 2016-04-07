package fr.mrcraftcod.utils;

import java.io.File;
public class FileUtils
{
	public static File getAppDataFolder()
	{;
		return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\");
	}
}
