package fr.raksrinana.utils.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ThreadLoop extends Thread{
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLoop.class);
	@Getter
	private SimpleBooleanProperty runningProperty = new SimpleBooleanProperty(true);
	@Getter
	private SimpleBooleanProperty pauseProperty = new SimpleBooleanProperty(false);
	
	@Override
	public void run(){
		onStart();
		while(!this.isInterrupted() && this.isRunning()){
			try{
				if(!isPaused()){
					this.loop();
				}
				else{
					Thread.sleep(500);
				}
			}
			catch(Exception e){
				LOGGER.error("ThreadLoop unhandled exception in loop", e);
				break;
			}
		}
	}
	
	/**
	 * Method called when the thread starts.
	 */
	public void onStart(){
	}
	
	/**
	 * Stops the loop.
	 */
	public void close(){
		this.interrupt();
		this.getRunningProperty().set(false);
		this.onClosed();
	}
	
	/**
	 * Called when the thread ends.
	 */
	public void onClosed(){}
	
	/**
	 * Pause the loop.
	 */
	public void pause(){
		this.getPauseProperty().set(true);
	}
	
	/**
	 * The method called every loop.
	 *
	 * @throws Exception If something happened.
	 */
	public abstract void loop() throws Exception;
	
	/**
	 * Unpause the loop.
	 */
	public void unpause(){
		this.getPauseProperty().set(false);
	}
	
	/**
	 * Tells if the loop is paused.
	 *
	 * @return True if paused, false otherwise.
	 */
	public boolean isPaused(){
		return getPauseProperty().get();
	}
	
	/**
	 * Tells if the loop is running.
	 *
	 * @return True if running, false otherwise.
	 */
	public boolean isRunning(){
		return this.getRunningProperty().get();
	}
}
