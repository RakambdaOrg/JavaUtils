package fr.raksrinana.utils.javafx.components;

import fr.raksrinana.utils.base.FileUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.annotation.Nullable;
import java.nio.file.Path;

public class FileField extends TextField{
	private final SimpleObjectProperty<Path> selectedFile;
	
	public FileField(boolean allowNull, @Nullable Path file){
		super();
		setEditable(false);
		setText("None");
		selectedFile = new SimpleObjectProperty<>(null);
		selectedFile.addListener(((observable, oldValue, newValue) -> {
			if(!allowNull && (newValue == null || !newValue.toFile().exists())){
				selectedFile.set(oldValue);
			}
			else{
				setText(newValue == null ? "None" : newValue.toAbsolutePath().toString());
			}
		}));
		selectedFile.set(file == null ? FileUtils.getHomeFolder() : file);
		setOnMouseClicked(e -> {
			setDisable(true);
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Select directory");
			directoryChooser.setInitialDirectory(selectedFile.get().toFile());
			selectedFile.set(directoryChooser.showDialog(new Stage()).toPath());
			setDisable(false);
		});
	}
	
	public Path getFile(){
		return selectedFile.get();
	}
}
