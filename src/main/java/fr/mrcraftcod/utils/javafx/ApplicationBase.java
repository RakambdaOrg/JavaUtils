package fr.mrcraftcod.utils.javafx;

import fr.mrcraftcod.utils.Callback;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
			this.getStageHandler().call(stage);
		stage.show();
		if(getOnStageDisplayed() != null)
			this.getOnStageDisplayed().call(stage);
	}

	public abstract String getFrameTitle();

	public abstract Callback<Stage> getStageHandler();

	public abstract Callback<Stage> getOnStageDisplayed();

	public abstract Parent createContent(Stage stage);
}
