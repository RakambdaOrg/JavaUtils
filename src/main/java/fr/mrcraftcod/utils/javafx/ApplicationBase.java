package fr.mrcraftcod.utils.javafx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.function.Consumer;

public abstract class ApplicationBase extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		Scene scene = new Scene(createContent(stage));
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getStageHandler() != null)
			this.getStageHandler().accept(stage);
		stage.show();
		if(getOnStageDisplayed() != null)
			this.getOnStageDisplayed().accept(stage);
	}

	public abstract String getFrameTitle();

	public abstract Consumer<Stage> getStageHandler();

	public abstract Consumer<Stage> getOnStageDisplayed();

	public abstract Parent createContent(Stage stage);
}