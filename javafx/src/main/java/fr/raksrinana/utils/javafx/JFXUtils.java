package fr.raksrinana.utils.javafx;

import fr.raksrinana.utils.base.ImageUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.NonNull;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class JFXUtils{
	@NonNull
	public static Optional<File> askDirectory(){
		return askDirectory(null);
	}
	
	@NonNull
	public static Optional<File> askDirectory(Path defaultFolder){
		try{
			return launchJFX(() -> {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("Select directory");
				if(Objects.nonNull(defaultFolder) && defaultFolder.toFile().exists()){
					directoryChooser.setInitialDirectory(defaultFolder.toFile());
				}
				return directoryChooser.showDialog(new Stage());
			});
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@NonNull
	public static <T> Optional<T> launchJFX(@NonNull Supplier<T> supplier) throws InterruptedException{
		final SimpleObjectProperty<T> result = new SimpleObjectProperty<>(null);
		boolean implicitExit = Platform.isImplicitExit();
		Platform.setImplicitExit(false);
		CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			Platform.runLater(() -> {
				result.set(supplier.get());
				latch.countDown();
			});
		});
		latch.await();
		Platform.setImplicitExit(implicitExit);
		return Optional.ofNullable(result.get());
	}
	
	@NonNull
	public static Optional<File> askFile(){
		return askFile(null);
	}
	
	@NonNull
	public static Optional<File> askFile(Path defaultFile){
		try{
			return launchJFX(() -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select file");
				if(Objects.nonNull(defaultFile) && defaultFile.toFile().exists()){
					fileChooser.setInitialDirectory(defaultFile.toFile());
				}
				return fileChooser.showOpenDialog(new Stage());
			});
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@NonNull
	public static Optional<List<File>> askFiles(){
		return askFiles(null);
	}
	
	@NonNull
	public static Optional<List<File>> askFiles(Path defaultFile){
		try{
			return launchJFX(() -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select file");
				if(Objects.nonNull(defaultFile) && defaultFile.toFile().exists()){
					fileChooser.setInitialDirectory(defaultFile.toFile());
				}
				return fileChooser.showOpenMultipleDialog(new Stage());
			});
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@NonNull
	public Optional<WritableImage> getImage(@NonNull URL url, int width, int height){
		try{
			return Optional.of(SwingFXUtils.toFXImage(ImageUtils.resizeBufferedImage(ImageIO.read(url), width, height), null));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
