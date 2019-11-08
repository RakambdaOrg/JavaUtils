package fr.raksrinana.utils.base;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.WinDef;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileUtils{
	/**
	 * Get a path inside the app data folder.
	 *
	 * @param path The path inside the app data folder.
	 *
	 * @return The path.
	 */
	@Nonnull
	public static Path getAppDataFolder(@Nonnull Path path){
		return getAppDataFolder().resolve(path);
	}
	
	/**
	 * Get the app data folder.
	 *
	 * @return The app data folder.
	 *
	 * @throws IllegalStateException If the OS is unknown.
	 */
	@Nonnull
	public static Path getAppDataFolder() throws IllegalStateException{
		final var os = OSUtils.getOs();
		switch(os){
			case WIN:
				return Paths.get(System.getenv("AppData"));
			case LINUX:
				return getHomeFolder();
			case OSX:
				return getHomeFolder().resolve("Library").resolve("Application Support");
		}
		throw new IllegalStateException("Unknown OS type");
	}
	
	/**
	 * Get the home folder.
	 *
	 * @return The home folder.
	 */
	@Nonnull
	public static Path getHomeFolder(){
		return Paths.get(System.getProperty("user.home"));
	}
	
	/**
	 * Get a path inside the desktop folder.
	 *
	 * @param path The path inside the desktop folder.
	 *
	 * @return The path.
	 */
	@Nonnull
	public static Path getDesktopFolder(@Nonnull Path path){
		return getDesktopFolder().resolve(path);
	}
	
	/**
	 * Get the desktop folder.
	 *
	 * @return The desktop folder.
	 */
	@Nonnull
	public static Path getDesktopFolder(){
		if(OSUtils.getOs() == OSUtils.OS.WIN){
			char[] pszPath = new char[WinDef.MAX_PATH];
			Shell32.INSTANCE.SHGetFolderPath(null, ShlObj.CSIDL_DESKTOPDIRECTORY, null, ShlObj.SHGFP_TYPE_CURRENT, pszPath);
			return Paths.get(Native.toString(pszPath));
		}
		return getHomeFolder().resolve("Desktop");
	}
	
	/**
	 * Get a path inside the home folder.
	 *
	 * @param path The path inside the home folder.
	 *
	 * @return The path.
	 */
	@Nonnull
	public static Path getHomeFolder(@Nonnull Path path){
		return getHomeFolder().resolve(path);
	}
	
	/**
	 * Remove forbidden characters inside a filename.
	 *
	 * @param name The filename to sanitize.
	 *
	 * @return The filename without forbidden characters.
	 */
	@Nonnull
	public static String sanitizeFileName(@Nonnull String name){
		return name.chars().mapToObj(i -> (char) i).filter(c -> Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == ' ' || c == '.').map(String::valueOf).collect(Collectors.joining());
	}
	
	/**
	 * Force delete a file.
	 *
	 * @param file The file to delete.
	 *
	 * @return true if no exceptions were thrown, false otherwise.
	 */
	public static boolean forceDelete(@Nonnull Path file){
		try{
			org.apache.commons.io.FileUtils.forceDelete(file.toFile());
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
	@Nonnull
	public static Optional<Path> askDirectory(){
		return askDirectory(null);
	}
	
	/**
	 * Ask the user for a directory.
	 *
	 * @param defaultFile The initial directory.
	 *
	 * @return The directory selected.
	 */
	@Nonnull
	public static Optional<Path> askDirectory(@Nullable Path defaultFile){
		return getFile(defaultFile, JFileChooser.DIRECTORIES_ONLY);
	}
	
	@Nonnull
	private static Optional<Path> getFile(@Nullable Path defaultFile, int selectionMode){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory((defaultFile == null ? Paths.get(".") : defaultFile).toFile());
		fc.setFileSelectionMode(selectionMode);
		int returnVal = fc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			return Optional.of(Paths.get(fc.getSelectedFile().toURI()));
		}
		return Optional.empty();
	}
	
	/**
	 * Ask the user for a file.
	 *
	 * @return The file selected.
	 */
	@Nonnull
	public static Optional<Path> askFile(){
		return askFile(null);
	}
	
	/**
	 * Ask the user for a file.
	 *
	 * @param defaultFile The default file.
	 *
	 * @return The file selected.
	 */
	@Nonnull
	public static Optional<Path> askFile(@Nullable Path defaultFile){
		return getFile(defaultFile, JFileChooser.FILES_ONLY);
	}
}
