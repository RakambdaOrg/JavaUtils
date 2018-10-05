package fr.mrcraftcod.utils.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({
		"WeakerAccess",
		"unused"
})
public abstract class ThreadLoop extends Thread{
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLoop.class);
	
	private SimpleBooleanProperty running = new SimpleBooleanProperty(true);
	private SimpleBooleanProperty pause = new SimpleBooleanProperty(false);
	
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
	 * Tells if the loop is running.
	 *
	 * @return True if running, false otherwise.
	 */
	public boolean isRunning(){
		return this.runningProperty().get();
	}
	
	/**
	 * Tells if the loop is paused.
	 *
	 * @return True if paused, false otherwise.
	 */
	public boolean isPaused(){
		return this.pauseProperty().get();
	}
	
	/**
	 * The method called every loop.
	 *
	 * @throws Exception If something happened.
	 */
	public abstract void loop() throws Exception;
	
	/**
	 * Get the running property.
	 *
	 * @return The running property.
	 */
	public SimpleBooleanProperty runningProperty(){
		return this.running;
	}
	
	/**
	 * Get the pause property.
	 *
	 * @return The pause property.
	 */
	public SimpleBooleanProperty pauseProperty(){
		return this.pause;
	}
	
	/**
	 * Stops the loop.
	 */
	public void close(){
		this.interrupt();
		this.running.set(false);
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
		this.pauseProperty().set(true);
	}
	
	/**
	 * Unpause the loop.
	 */
	public void unpause(){
		this.pauseProperty().set(false);
	}
}
