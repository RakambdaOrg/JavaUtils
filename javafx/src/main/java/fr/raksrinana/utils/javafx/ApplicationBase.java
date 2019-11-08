package fr.raksrinana.utils.javafx;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Taskbar;
import java.util.function.Consumer;

@SuppressWarnings({
		"WeakerAccess",
		"unused"
})
public abstract class ApplicationBase extends Application{
	private Stage stage;
	
	public void start(@Nonnull Stage stage) throws Exception{
		this.stage = stage;
		preInit();
		Scene scene = buildScene(stage);
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getIcon() != null){
			setIcon(getIcon());
		}
		if(getStageHandler() != null){
			this.getStageHandler().accept(stage);
		}
		if(shouldDisplayAtStart()){
			stage.show();
			if(getOnStageDisplayed() != null){
				this.getOnStageDisplayed().accept(stage);
			}
		}
	}
	
	@Nonnull
	public Scene buildScene(@Nonnull Stage stage){
		return new Scene(createContent(stage));
	}
	
	@SuppressWarnings("RedundantThrows")
	public void preInit() throws Exception{}
	
	@Nullable
	public abstract String getFrameTitle();
	
	public boolean shouldDisplayAtStart(){
		return true;
	}
	
	@Nullable
	public Image getIcon(){
		return null;
	}
	
	private void setIcon(@Nonnull Image icon){
		this.stage.getIcons().clear();
		this.stage.getIcons().add(icon);
		if(Taskbar.isTaskbarSupported()){
			final var taskbar = Taskbar.getTaskbar();
			if(taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)){
				taskbar.setIconImage(SwingFXUtils.fromFXImage(icon, null));
			}
		}
	}
	
	@Nullable
	public abstract Consumer<Stage> getStageHandler();
	
	@Nullable
	public abstract Consumer<Stage> getOnStageDisplayed() throws Exception;
	
	@Nonnull
	public abstract Parent createContent(Stage stage);
	
	@Nonnull
	public Stage getStage(){
		return stage;
	}
}
