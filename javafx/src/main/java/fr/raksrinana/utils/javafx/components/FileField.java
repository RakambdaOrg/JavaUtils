package fr.raksrinana.utils.javafx.components;

import fr.raksrinana.utils.base.FileUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Getter;
import java.nio.file.Path;

public class FileField extends TextField{
	@Getter
	private final SimpleObjectProperty<Path> selectedFileProperty;
	
	public FileField(boolean allowNull, Path file){
		super();
		setEditable(false);
		setText("None");
		selectedFileProperty = new SimpleObjectProperty<>(null);
		selectedFileProperty.addListener(((observable, oldValue, newValue) -> {
			if(!allowNull && (newValue == null || !newValue.toFile().exists())){
				selectedFileProperty.set(oldValue);
			}
			else{
				setText(newValue == null ? "None" : newValue.toAbsolutePath().toString());
			}
		}));
		selectedFileProperty.set(file == null ? FileUtils.getHomeFolder() : file);
		setOnMouseClicked(e -> {
			setDisable(true);
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Select directory");
			directoryChooser.setInitialDirectory(selectedFileProperty.get().toFile());
			selectedFileProperty.set(directoryChooser.showDialog(new Stage()).toPath());
			setDisable(false);
		});
	}
	
	public Path getFile(){
		return this.getSelectedFileProperty().get();
	}
}
