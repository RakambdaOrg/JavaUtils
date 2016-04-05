package fr.mrcraftcod.utils;

import java.text.DecimalFormat;

public class StringUtils
{
	private final static String[] UNITS_PREFIX = {"", "K", "M", "G", "T", "P"};

	public static String getDownloadSizeText(double size)
	{
		int unit = 0;
		while(size >= 1024)
		{
			unit++;
			size /= 1024;
		}
		return new DecimalFormat("0.00").format(size) + " " + UNITS_PREFIX[unit] + "B";
	}
}
