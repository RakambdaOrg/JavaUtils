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
		Scene scene = new Scene(createContent());
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getStageHandler() != null)
			this.getStageHandler().call(stage);
		stage.show();
	}

	public abstract String getFrameTitle();

	public abstract Callback<Stage> getStageHandler();

	public abstract Parent createContent();
}
