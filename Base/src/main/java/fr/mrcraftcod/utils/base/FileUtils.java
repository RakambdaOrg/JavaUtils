package fr.mrcraftcod.utils.base;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class FileUtils{
	/**
	 * Get a path inside the app data folder.
	 *
	 * @param path The path inside the app data folder.
	 *
	 * @return The file.
	 */
	public static File getAppDataFolder(String path){
		return new File(getAppDataFolder(), path);
	}
	
	/**
	 * Get the app data folder.
	 *
	 * @return The app data folder.
	 */
	public static File getAppDataFolder(){
		if(OSUtils.isMac()){
			return new File(getHomeFolder(), "/Library/Application Support");
		}
		return new File(getHomeFolder(), "AppData\\Roaming\\");
	}
	
	/**
	 * Get the home folder.
	 *
	 * @return The home folder.
	 */
	public static File getHomeFolder(){
		return new File(System.getProperty("user.home"));
	}
	
	/**
	 * Create the parent directories for the given file.
	 *
	 * @param file The file to create directories for.
	 */
	public static void createDirectories(File file){
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
	}
	
	/**
	 * Get a path inside the desktop folder.
	 *
	 * @param path The path inside the desktop folder.
	 *
	 * @return The file.
	 */
	public static File getDesktopFolder(String path){
		return new File(getDesktopFolder(), path);
	}
	
	/**
	 * Get the desktop folder.
	 *
	 * @return The desktop folder.
	 */
	public static File getDesktopFolder(){
		return new File(getHomeFolder(), "Desktop");
	}
	
	/**
	 * Get a path inside the home folder.
	 *
	 * @param path The path inside the home folder.
	 *
	 * @return The file.
	 */
	public static File getHomeFolder(String path){
		return new File(getHomeFolder(), path);
	}
	
	/**
	 * Remove forbidden characters inside a filename.
	 *
	 * @param name The filename to sanitize.
	 *
	 * @return The filename without forbidden characters.
	 */
	public static String sanitizeFileName(String name){
		return name.chars().mapToObj(i -> (char) i).filter(c -> Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == ' ' || c == '.').map(String::valueOf).collect(Collectors.joining());
	}
	
	/**
	 * Force delete a file.
	 *
	 * @param file The file to delete.
	 *
	 * @return true if no exceptions were thrown, false otherwise.
	 */
	public static boolean forceDelete(File file){
		try{
			org.apache.commons.io.FileUtils.forceDelete(file);
			return true;
		}
		catch(IOException ignored){
		}
		return false;
	}
	
	/**
	 * Ask the user for a directory.
	 *
	 * @return The directory selected.
	 */
	public static File askDirectory(){
		return askDirectory(null);
	}
	
	/**
	 * Ask the user for a directory.
	 *
	 * @param defaultFile The initial directory.
	 *
	 * @return The directory selected.
	 */
	public static File askDirectory(File defaultFile){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultFile == null ? new File(".") : defaultFile);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			return fc.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * Ask the user for a file.
	 *
	 * @return The file selected.
	 */
	public static File askFile(){
		return askFile(null);
	}
	
	/**
	 * Ask the user for a file.
	 *
	 * @param defaultFile The default file.
	 *
	 * @return The file selected.
	 */
	private static File askFile(File defaultFile){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultFile == null ? new File(".") : defaultFile);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			return fc.getSelectedFile();
		}
		return null;
	}
}
